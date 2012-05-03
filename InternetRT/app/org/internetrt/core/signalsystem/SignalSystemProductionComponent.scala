package org.internetrt.core.signalsystem
import org.internetrt.persistent.RoutingResourcePoolComponents
import org.internetrt.core.signalsystem.workflow.WorkflowEngineComponents


trait SignalSystemProductionComponent extends SignalSystemComponent{
        this:RoutingResourcePoolComponents with WorkflowEngineComponents=>

  val signalSystem = new SignalSystemImpl()
  
  class SignalSystemImpl extends SignalSystem{
          
    private def getRoutingInstance(s:Signal) = workflowEngine.getRoutingInstance(s)
    
    private def getRouting(s:Signal)={
      val signalID = s.getIdentifier()
      routingResourcePool.getRoutingBySignal(signalID);
    }
    
    def handleSignal(s:Signal):SignalResponse={
      if(getRoutingInstance(s) == null){
        val routing = getRouting(s)
        workflowEngine.initWorkflow(routing)
      }
      val ins = workflowEngine.getRoutingInstance(s)
      return new ObjectResponse(ins)
    }
    def getHeadResponse(s:Signal):SignalResponse={
      val ins = getRoutingInstance(s)
      if(ins == null){
        return new ObjectResponse(getRouting(s));
      }else
    	return new ObjectResponse(ins)
    }
  } 
}