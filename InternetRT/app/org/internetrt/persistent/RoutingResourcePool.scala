package org.internetrt.persistent
import org.internetrt.core.model.Routing


	trait RoutingResourcePool {
		def getRoutingBySignal(signaluri:String):Option[Routing];
		
		def saveRouting(r:Routing):Boolean;
	}


	class MemoryRoutingResourcePool extends RoutingResourcePool {
	    val a:scala.collection.mutable.Map[String,Routing]  = scala.collection.mutable.Map.empty[String,Routing] 
		//a.put("")
	    
	    def getRoutingBySignal(signalID:String):Option[Routing] = {
		   a.get(signalID)
		}
	    def saveRouting(r:Routing)=false
	}
