package org.internetrt.core.io
import akka.dispatch.Future

trait IOManagerComponent{
  val ioManager:IOManager;
 
  trait IOManager {
    def sendToClient(uid:String,msg:String,allowedStatus:Seq[String])
    def readFromClient(uid:String,msg:String,allowedStatus:Seq[String]):Future[String]
  }
}