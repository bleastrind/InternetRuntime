package org.internetrt.core.signalsystem.workflow
import org.internetrt.core.model.Routing
import org.internetrt.core.model.RoutingInstance
import org.internetrt.core.signalsystem.Signal

	trait WorkflowEngine {
		def initWorkflow(routing:Routing):RoutingInstance
		
		def getRoutingInstance(s:Signal):RoutingInstance
		
		def getRoutingInstaceByworkflowID(workflowID:String):RoutingInstance
	}
