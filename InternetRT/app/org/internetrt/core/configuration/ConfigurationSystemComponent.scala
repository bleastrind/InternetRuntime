package org.internetrt.core.configuration
import org.internetrt.core.model.Routing
import org.internetrt.core.model.Application



trait ConfigurationSystem{
    def confirmRouting(userID:String,app:String,r:Routing)
    def getRoutingsBySignal(signalID:String):Seq[Routing]
    def installApp(app:Application)
    def getAppSecretByID(appID:String):String
}
