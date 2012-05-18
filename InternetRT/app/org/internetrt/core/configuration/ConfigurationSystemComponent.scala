package org.internetrt.core.configuration
import org.internetrt.core.model.Routing



trait ConfigurationSystemComponent {
  val configurationSystem:ConfigurationSystem
  
  trait ConfigurationSystem{
    def confirmRouting(userID:String,app:String,r:Routing)
  }
}