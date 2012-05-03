package org.internetrt.persistent
import org.internetrt.core.model.Routing

trait RoutingResourcePoolComponents{
	val routingResourcePool:RoutingResourcePool

	trait RoutingResourcePool {
		def getRoutingBySignal(signalID:String):Routing;
	}

}