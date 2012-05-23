package org.internetrt.core.configuration
import org.internetrt.core.model.Routing
import org.internetrt.core.model.Application



  trait ConfigurationSystem{
    def confirmRouting(userID:String,app:String,r:Routing)
    def installApp(app:Application)
  }

trait StubConfigurationSystem extends ConfigurationSystem{
    def confirmRouting(userID:String,app:String,r:Routing){}
    def installApp(app:Application)  {}
}