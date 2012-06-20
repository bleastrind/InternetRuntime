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
  
  var marketappid:String = null
  def installmarket={
    TestEnvironment.registerApp("market","secret");
    marketappid = TestUserInterface.installRootApp("uid","""<AppOwner>market</AppOwner>""");
    
    marketappid !== null
  }
  
  var normalappid:String = null
  def installApp = {
    //Before install to market, From app
    TestEnvironment.registerApp("app","sec2")
    
    //From market
    val code = TestEnvironment.getAuthcodeForServerFlow(marketappid,"uid","http")
    val accessToken = TestEnvironment.getAccessTokenByAuthtoken(marketappid,code,"secret")
		 
    normalappid = TestEnvironment.installApplication(accessToken.value,"""<?xml version="1.0" encoding="UTF-8" ?><AppOwner>app</AppOwner>""")
    normalappid !== null
  }
  
  def query = {
    val code = TestEnvironment.getAuthcodeForServerFlow(normalappid,"uid","http")
    val accessToken = TestEnvironment.getAccessTokenByAuthtoken(normalappid,code,"sec2")
	
    val apps = TestEnvironment.getApplications(accessToken.value)
    apps.contains(normalappid) and apps.contains(marketappid)
  }
  
}
