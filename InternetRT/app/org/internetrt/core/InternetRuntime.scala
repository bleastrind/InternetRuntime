package org.internetrt.core

import model.RoutingInstance
import signalsystem.Signal
import signalsystem.SignalResponse
import org.internetrt.core.signalsystem.ObjectResponse
import org.internetrt.core.security.AccessToken
import org.internetrt.core.security.AuthCenter
import org.internetrt.core.signalsystem.SignalSystem
import org.internetrt.core.io.IOManager
import org.internetrt.core.configuration.ConfigurationSystem


/**
 * The Facade of the logical system
 */
abstract class InternetRuntime{
    
  object errReport extends {
    val global:InternetRuntime.this.type = InternetRuntime.this
  } 
   
  // sub-components --------------------------------------------------
  val signalSystem:SignalSystem
  val authCenter:AuthCenter
  val ioManager:IOManager
  val confSystem:ConfigurationSystem
  
  def getHeadResponse(s:Signal):SignalResponse = {
    signalSystem.getHeadResponse(s)
  }
  def executeSignal(s:Signal):SignalResponse = {
    signalSystem.handleSignal(s)
  }
  def getAuthcodeForWorkflow(appID:String,appSecret:String,workflowID:String)={
    authCenter.genAuthCode(appID,appSecret,workflowID)
  }
  def getAuthcodeForServerFlow(appID:String,userID:String,redirect_uri:String):String={
    authCenter.genAuthCode(appID,userID);
  }
  def getUserAndFromByAccesstoken(accesstoken:String)={
    authCenter.getUserIDAppIDPair(accesstoken)
  }
  def getAccessTokenByAuthtoken(appID:String,authtoken:String,appSecret:String):AccessToken={
    authCenter.genAccessTokenByAuthToken(authtoken,appID,appSecret)
  }
  
}


trait StubSignalSystem extends SignalSystem{

	    def handleSignal(t:Signal):SignalResponse={
	          new ObjectResponse("Stub")
	    }
	    def getHeadResponse(t:Signal):SignalResponse={
	         new ObjectResponse("Stub")
	    }

}


