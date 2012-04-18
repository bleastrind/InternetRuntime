package org.internetrt.core.signalsystem

trait SignalSystemComponent {
  val signalSystem:SignalSystem
  
  trait SignalSystem{
    def handleSignal(t:Signal):SignalResponse
  }
}