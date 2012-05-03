package org.internetrt.core

import configuration.ConfigurationSystemComponent
import io.IOManagerComponent
import model.RoutingInstance
import signalsystem.Signal
import signalsystem.SignalResponse

import signalsystem.SignalSystemComponent
import org.internetrt.core.signalsystem.ObjectResponse

/**
 * Trait Components represent the entire combined pure logical system
 */
trait Components extends SignalSystemComponent 
with ConfigurationSystemComponent 
with IOManagerComponent{
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
  val configurationSystem = new ConfigurationSystem{
  }
}

trait StubIOManagerComponent extends IOManagerComponent{
  val ioManager = null
}