package org.internetrt.test
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2._
import org.specs2.specification._
import org.internetrt.core.InternetRuntime
import org.internetrt.core.signalsystem.SignalSystem
import org.internetrt.core.io._
import org.internetrt.core.security._
import org.internetrt.core.configuration._
import org.internetrt.MemoryAuthCenter
import org.specs2.execute.Result

class OAuthServerSpec extends Specification with Mockito{override def is =
    "This is a specification to check the oauth server works well"         ^
                                                                                p^
//    "The normal signal should"                                                   ^
//      "[Function]notify the intereptors"                                     ! e1^
//      "[Safe]start with 'Hello'"                                             ! e2^
//      "[Performance]end with 'world'"                                        ! e3^
//                                                                                p^
    "The client should"  ^
      "Get the auth code first with appID:${appid} and userID:${userid}" ^ (auth) ^
      "ask access code with auth code and appID:${appid},appSec:${secret}" ^ (access) ^
      "access resource with access code" ^ (request) ^
      end
    
//    def e1 = "Hello world" must have size(11)
//    def e2 = "Hello world" must startWith("Hello")
//    def e3 = "Hello world" must endWith("world")

    
    object TestEnvironment extends InternetRuntime{
		val signalSystem = mock[SignalSystem]
		
		object authCenter extends{
			val global = TestEnvironment.this

		}with MemoryAuthCenter{
		  			
			override def getUserIDAppIDPair(accessToken:String):(String,String)={
			  super.getUserIDAppIDPair(accessToken)
			}
		}
		
		val ioManager= mock[IOManager]
		val confSystem=mock[ConfigurationSystem]
		
	}
	object auth extends Given[String]{
		def extract(text: String):String =	{
		  val (a,u)= extract2(text)

		  TestEnvironment.getAuthcodeForServerFlow(a,u,"http")
		}
	}	
	object access extends When[String,(AccessToken,String)]{
		def extract(p:String, text:String) = {
		  val (a,s) = extract2(text)
		  (TestEnvironment.getAccessTokenByAuthtoken(a,p,s),s) //TODO secret is not installed??!
		}
	}
	object request extends Then[(AccessToken,String)]{
	  def extract(p:(AccessToken,String), text:String):Result = {
	    val (app,user) = TestEnvironment.authCenter.getUserIDAppIDPair(p._1.value);
	    val uid = TestEnvironment.getUserIDByAccessToken(p._1.value,p._2)
	    app == "appid" and user == "userid" and user == uid
	  
	  }
	}

}