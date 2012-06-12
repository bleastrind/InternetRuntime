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
import org.internetrt.MemoryConfigurationSystem
import org.internetrt.core.model.Application

@RunWith(classOf[JUnitRunner])
class SignalSpedification extends Specification with Mockito{ override def is =
    "This is a specification to check the signal dispatching works well"         ^
                                                                                p^
    "Preparation"																^
    	"""The application should registered as "appid" with secret "secret"  """  ! install1^
    	"""The application2 should registered as "appid2" with secret "secret2"  """  ! install2^
                                                                                p^
    "The sip type signal should"  ^
      "Given the sip request" ^ (request) ^
      "Receive a sip response and sendTo another client" ^ (sip) ^
      "Ask accesstoken and userID from routinginstance id" ^ (requesttoclient) ^
      "Received the result" ^ (data) ^
      end
    

	object TestEnvironment extends InternetRuntime{
		object signalSystem extends{
			val global = TestEnvironment.this
		}with MemorySignalSystem
		
		object authCenter extends{
			val global = TestEnvironment.this

		}with MemoryAuthCenter
		
		object confSystem extends{
			val global = TestEnvironment.this 
		}with MemoryConfigurationSystem
		
		val ioManager= mock[IOManager]
		
	}
    def install1 = {
      TestEnvironment.confSystem.installApp(Application("appid","secret",null))
	  success
	}
    def install2 = {
      TestEnvironment.confSystem.installApp(Application("appid2","secret2",null))
      success
    }

	object request extends Given[SignalResponse]{
		def extract(text: String):SignalResponse = {
		  val code = TestEnvironment.getAuthcodeForServerFlow("appid","user","http")
		  val accessToken = TestEnvironment.getAccessTokenByAuthtoken("appid",code,"secret")
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
	    val authcode = TestEnvironment.getAuthcodeForActionFlow("appid2","secret2",p._2);
	    val accesstoken = TestEnvironment.getAccessTokenByAuthtoken("appid2",authcode,"secret2");
	    TestEnvironment.getUserIDByAccessToken(accesstoken.value,"secret2")// execute return the routing instace
	  }
	}
	object data extends Then[String]{
	  def extract(p:String, text:String) = p === "user"
	}
  }