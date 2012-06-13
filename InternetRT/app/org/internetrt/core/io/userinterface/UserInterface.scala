package org.internetrt.core.io.userinterface
import org.internetrt.core.security.AuthCenter

abstract class UserInterface {
  val authCenter: AuthCenter
  val clientManager = ClientsManager
  
  def register(username: String, password: String): String = {
    if (authCenter.register(username, password)) "success" else "failed"
  }
  def login(username: String, password: String): String = {
    authCenter.login(username, password)
  }
  def response(uid: String, msg: String, msgID: String) = {
    clientManager.response(uid, msg, msgID)
  }

  def join(uid: String, driver: ClientDriver) = {
    clientManager.join(uid, driver)
  }
}