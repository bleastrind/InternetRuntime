package org.internetrt.core.signalsystem
import org.internetrt.persistent._
import org.internetrt.core.signalsystem.workflow._
import org.internetrt.core.I18n
import org.internetrt.core.model.RoutingInstance


  abstract class SignalSystemImpl extends SignalSystem{
    
  val workflowEngine:WorkflowEngine
  val routingResourcePool:RoutingResourcePool
  
    private def getRoutingInstance(s:Signal) = workflowEngine.getRoutingInstance(s)
    
    private def getRouting(s:Signal)={
      val signalID = s.from+s.user+s.uri;
      routingResourcePool.getRoutingBySignal(signalID);
    }
    
    def handleSignal(s:Signal):SignalResponse={
      if(getRoutingInstance(s) == null){
        getRouting(s) match{
          case Some(r) => workflowEngine.initWorkflow(r)
          case None => return new RejectResponse(I18n.REJECT)
        }
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
    
    def getRoutingInstaceByworkflowID(workflowID:String):RoutingInstance={
      workflowEngine.getRoutingInstaceByworkflowID(workflowID)
    }
  } 
