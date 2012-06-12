package org.internetrt.core.configuration
import org.internetrt.persistent.AppPool
import org.internetrt.core.model._

abstract class ConfigurationSystemImpl extends AnyRef 
  with ConfigurationSystem{
  
  val appPool:AppPool
  
  def confirmRouting(userID:String,app:String,r:Routing){}
  def installApp(app:Application)={
    appPool.installApplication(app.id,app)
  }
  def getAppSecretByID(appID:String)={
    appPool.getAppSecretByID(appID)
  }
}