package org.internetrt.core.configuration
import org.internetrt.persistent.AppPool
import org.internetrt.core.model._
import org.internetrt.persistent.RoutingResourcePool

abstract class ConfigurationSystemImpl extends AnyRef 
  with ConfigurationSystem{
  
  import global.ioManager
  
  val appPool:AppPool
  val routingResourcePool:RoutingResourcePool
  
  def confirmRouting(userID:String,r:Routing){
    //val requests = r.xml \ "requests"
    routingResourcePool.saveRouting(r)
  }
  def getRoutingsBySignal(signalID:String)={
    routingResourcePool.getRoutingsBySignal(signalID)
  }
  
  def installApp(app:Application)={
    appPool.installApplication(app.id,app)
  }
  def getAppSecretByID(appID:String)={
    appPool.getAppSecretByID(appID)
  }
}