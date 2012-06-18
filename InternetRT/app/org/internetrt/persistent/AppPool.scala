package org.internetrt.persistent
import org.internetrt.core.model.Application

trait AppPool {
    def installApplication(userID:String, id:String, app:Application)
	def getAppSecretByID(userID:String, id:String):String
	def getApp(userID:String, id:String):Application
	def getAppIDsByUserID(userID:String):Seq[String]
}

class StubAppPool extends AppPool{
  val innerMap = scala.collection.mutable.Map.empty[String,Application]
  def installApplication(userID:String, id:String, app:Application) = {
    System.out.println("Warnning:Using stubapppool! Only one user is valid!")
    innerMap += (id -> app)
  }
  def getAppSecretByID(userID:String, id:String)={
	  getApp(userID, id).secret
  }
  
  def getApp(userID:String, id:String) = {
      innerMap.get(id) match{
      case Some(app) => app
      case None => null
    }
  }
  
  def getAppIDsByUserID(userID:String):Seq[String] = {
    innerMap.keys.toSeq
  }
}