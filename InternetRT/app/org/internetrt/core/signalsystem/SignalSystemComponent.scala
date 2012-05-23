package org.internetrt.core.signalsystem
import org.internetrt.core.InternetRuntime
import org.internetrt.core.model.RoutingInstance


  trait SignalSystem{
  
    val global:InternetRuntime
    
    def handleSignal(t:Signal):SignalResponse
    def getHeadResponse(t:Signal):SignalResponse
    
    def getRoutingInstaceByworkflowID(workflowID:String):RoutingInstance
  }
