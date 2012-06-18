package org.internetrt.core.configuration
import org.internetrt.persistent.AppPool
import org.internetrt.core.model._
import org.internetrt.persistent.RoutingResourcePool
import org.internetrt.persistent.UserAppRelationPool

abstract class ConfigurationSystemImpl extends AnyRef 
  with ConfigurationSystem{
  
  import global.ioManager
  
  val appPool:AppPool
  val routingResourcePool:RoutingResourcePool
  val userAppRelationPool:UserAppRelationPool
  
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

  def getAppIDs(userID:String):Seq[String]={
    userAppRelationPool.getAppIDsByUserID(userID)
  }
  def getApp(id:String):Application ={
    appPool.getApp(id)
  }
	
  def getAppSecretByID(appID:String)={
    appPool.getAppSecretByID(appID)
  }
}