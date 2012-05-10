package org.internetrt.core

import configuration.ConfigurationSystemComponent
import io.IOManagerComponent
import model.RoutingInstance
import signalsystem.Signal
import signalsystem.SignalResponse
import signalsystem.SignalSystemComponent
import org.internetrt.core.signalsystem.ObjectResponse
import org.internetrt.core.security.SecurityPrivacyComponent
import org.internetrt.core.security.AccessToken

/**
 * Trait Components represent the entire combined pure logical system
 */
trait Components extends SignalSystemComponent 
with ConfigurationSystemComponent 
with IOManagerComponent
with SecurityPrivacyComponent{
}

/**
 * The Facade of the logical system
 */
abstract class InternetRuntime{
  val components:Components
  def getHeadResponse(s:Signal):SignalResponse = {
    components.signalSystem.getHeadResponse(s)
  }
  def executeSignal(s:Signal):SignalResponse = {
    components.signalSystem.handleSignal(s)
  }
  def getAuthcodeForServerFlow(appID:String,userID:String,redirect_uri:String):String={
    components.authCenter.getAuthCode(appID,userID);
  }
  
  def getAccessTokenByAuthtoken(appID:String,authtoken:String,appSecret:String):AccessToken={
    components.authCenter.genAccessTokenByAuthToken(authtoken,appID,appSecret)
  }
  
}


trait StubSignalSystemComponent extends SignalSystemComponent{
  val signalSystem = new SignalSystem{
	    def handleSignal(t:Signal):SignalResponse={
	          new ObjectResponse("Stub")
	    }
	    def getHeadResponse(t:Signal):SignalResponse={
	         new ObjectResponse("Stub")
	    }
	  }
}
trait StubConfigurationSystemComponent extends ConfigurationSystemComponent{
  val configurationSystem = null
}

trait StubIOManagerComponent extends IOManagerComponent{
  val ioManager = null
}

trait StubSecurityPrivacyComponent extends SecurityPrivacyComponent{
  val authCenter = null
}