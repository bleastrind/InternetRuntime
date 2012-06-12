package org.internetrt.test
import org.specs2.mutable.Specification
import org.specs2._
import org.specs2.specification._
import org.specs2.mock.Mockito
import org.internetrt.core._
import org.internetrt.core.signalsystem.SignalSystem
import org.internetrt.core.security.AuthCenter
import org.internetrt.core.io.IOManager
import org.internetrt.core.configuration.ConfigurationSystem
import org.internetrt.MemorySignalSystem
import org.internetrt.core.signalsystem.Signal
import org.internetrt.core.signalsystem.SignalResponse
import org.internetrt.MemoryAuthCenter
import org.internetrt.core.signalsystem.ObjectResponse
import org.internetrt.core.signalsystem.ObjectResponse
import org.internetrt.core.model.RoutingInstance
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SignalSpedification extends Specification with Mockito{ override def is =
    "This is a specification to check the signal dispatching works well"         ^
                                                                                p^
    "The normal signal should"                                                   ^
      "[Function]notify the intereptors"                                     ! e1^
      "[Safe]start with 'Hello'"                                             ! e2^
      "[Performance]end with 'world'"                                        ! e3^
                                                                                p^
    "The sip type signal should"  ^
      "Given the sip request" ^ (request) ^
      "Receive a sip response and sendTo another client" ^ (sip) ^
      "Ask accesstoken and userID from routinginstance id" ^ (requesttoclient) ^
      "Received the result" ^ (data) ^
      end
    
    def e1 = "Hello world" must have size(11)
    def e2 = "Hello world" must startWith("Hello")
    def e3 = "Hello world" must endWith("world")
	object TestEnvironment extends InternetRuntime{
		object signalSystem extends{
			val global = TestEnvironment.this
		}with MemorySignalSystem
		object authCenter extends{
			val global = TestEnvironment.this

		}with MemoryAuthCenter
		val ioManager= mock[IOManager]
		val confSystem=mock[ConfigurationSystem]
		
	}


	object request extends Given[SignalResponse]{
		def extract(text: String):SignalResponse = {
		  val code = TestEnvironment.getAuthcodeForServerFlow("app","user","http")
		  val accessToken = TestEnvironment.getAccessTokenByAuthtoken("app",code,"secret")
		  TestEnvironment.initActionFromThirdPart(accessToken.value,"signalname",null,null) // head response return the routing

		}
	}	
	object sip extends When[SignalResponse,(String,String)]{
		def extract(p:SignalResponse, text:String):(String,String) = {
		  val data = "";
		  val routingInstaceID = p.asInstanceOf[ObjectResponse].asObject[RoutingInstance].id;
		  (data,routingInstaceID)
		}
	}
	object requesttoclient extends When[(String,String),String]{
	  def extract(p:(String,String), text:String) = {
	    val authcode = TestEnvironment.getAuthcodeForActionFlow("app2","sec2",p._2);
	    val accesstoken = TestEnvironment.getAccessTokenByAuthtoken("app2",authcode,"sec2");
	    TestEnvironment.getUserIDByAccessToken(accesstoken.value,"sec2")// execute return the routing instace
	  }
	}
	object data extends Then[String]{
	  def extract(p:String, text:String) = p === "user"
	}
  }