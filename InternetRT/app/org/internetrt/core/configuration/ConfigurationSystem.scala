package org.internetrt.core.configuration
import org.internetrt.core.model.Routing
import org.internetrt.core.model.Application
import org.internetrt.core.InternetRuntime



trait ConfigurationSystem{
	val global:InternetRuntime
    
	def confirmRouting(userID:String,r:Routing)
    def getRoutingsBySignal(signalID:String):Seq[Routing]
    def installApp(app:Application)
    def getAppSecretByID(appID:String):String
}
