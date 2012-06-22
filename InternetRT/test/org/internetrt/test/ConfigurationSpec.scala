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
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.internetrt.persistent.AppPool
import org.internetrt.persistent.StubInternalUserPool
import org.internetrt.persistent.StubAccessTokenPool
import org.internetrt.persistent.StubAuthCodePool
import org.internetrt.core.io.userinterface.UserInterface
import org.internetrt.core.io.userinterface.ClientDriver
import org.internetrt.MemoryAccessControlSystem
import org.internetrt.MemorySignalSystem
import org.internetrt.MemoryConfigurationSystem

@RunWith(classOf[JUnitRunner])
class ConfigurationSpec extends Specification with Mockito {override def is =
  """This spec normalized the behaviours about application installation"""  ^
  																			p^
  "A root application market should be installed through userInterface"    !installmarket^
  "The market can install another app for the user"							! installApp ^
  "The others can query the application been installed"					!query
 object TestEnvironment extends InternetRuntime {
  object authCenter extends {
    val global = TestEnvironment.this
  } with MemoryAuthCenter

  object signalSystem extends {
    val global = TestEnvironment.this
  } with MemorySignalSystem

  object ioManager extends {
    val global = TestEnvironment.this
  } with IOManagerImpl

  object confSystem extends {
    val global = TestEnvironment.this
  } with MemoryConfigurationSystem
  
  object aclSystem extends {
    val global = TestEnvironment.this
  } with MemoryAccessControlSystem

  }
  object TestUserInterface extends UserInterface {
    val global = TestEnvironment
  }
  

  var appmarketid:String = null
  var appmarketsecret:String = null
  def installmarket={
    val (id,secret) = TestEnvironment.registerApp("a@market.com");
    appmarketid = id
    appmarketsecret = secret
    TestUserInterface.installRootApp("uid","<App><AppID>"+appmarketid+"</AppID><AppOwner>market</AppOwner></App>");
    
  }
  
  var normalid:String = null
  var normalsec:String = null
  def installApp = {
    //Before install to market, From app
    val (id,secret) = TestEnvironment.registerApp("aa@market.com")
        normalid = id
    normalsec = secret
    //From market
    val code = TestEnvironment.getAuthcodeForServerFlow(appmarketid,"uid","http")
    val accessToken = TestEnvironment.getAccessTokenByAuthtoken(appmarketid,code,appmarketsecret)
		 
    TestEnvironment.installApplication(accessToken.value,"""<?xml version="1.0" encoding="UTF-8" ?><App><AppID>"""+normalid+"""</AppID><AppOwner>app</AppOwner></App>""")
   
  }
  
  def query = {
    val code = TestEnvironment.getAuthcodeForServerFlow(normalid,"uid","http")
    val accessToken = TestEnvironment.getAccessTokenByAuthtoken(normalid,code,normalsec)
	
    val apps = TestEnvironment.getApplications(accessToken.value)
    apps.contains(normalid) and apps.contains(appmarketid)
  }
  
}
