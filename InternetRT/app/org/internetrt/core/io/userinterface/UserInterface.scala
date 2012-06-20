package org.internetrt.core.io.userinterface
import org.internetrt.core.security.AuthCenter
import org.internetrt.core.security.AccessControlSystem
import org.internetrt.core.InternetRuntime
import java.util.UUID
import org.internetrt.core.model.Application
import scala.xml.XML

abstract class UserInterface {
  
  val global:InternetRuntime
  import global.authCenter
  import global.aclSystem
  import global.confSystem
  
  val clientManager = ClientsManager
  
  def register(username: String, password: String): String = {
    if (authCenter.register(username, password)) "success" else "failed"
  }
  def login(username: String, password: String): String = {
    authCenter.login(username, password)
  }
  
  def installRootApp(uid:String,xml:String) = {
    val id = UUID.randomUUID().toString()
	    
	val app = Application(id,XML.loadString(xml))
	aclSystem.grantAccess(uid,id, app.accessRequests,true)
	confSystem.installApp(uid, app)
	
	id
  }
  
  def response(uid: String, msg: String, msgID: String) = {
    clientManager.response(uid, msg, msgID)
  }

  def join(uid: String, driver: ClientDriver) = {
    clientManager.join(uid, driver)
  }
}