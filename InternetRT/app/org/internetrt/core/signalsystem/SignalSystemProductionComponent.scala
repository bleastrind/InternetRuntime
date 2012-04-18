package org.internetrt.core.signalsystem

trait SignalSystemProductionComponent extends SignalSystemComponent{
  class SignalSystemImpl extends SignalSystem{
    def handleSignal(t:Signal)=null
  } 
}