package org.internetrt.persistent
import org.internetrt.core.model.Application

trait AppPool {
    def installApplication(id:String, app:Application)
	def getAppSecretByID(id:String):String
}

class StubAppPool extends AppPool{
  val innerMap = scala.collection.mutable.Map.empty[String,Application]
  def installApplication(id:String, app:Application) = {
    innerMap += (id -> app)
  }
  def getAppSecretByID(id:String)={
    innerMap.get(id) match{
      case Some(app) => app.secret
      case None => null
    }
  }
}