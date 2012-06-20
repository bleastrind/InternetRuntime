package org.internetrt.core.model
import org.internetrt.core.signalsystem.Signal

case class Application(id:String,xml:scala.xml.Elem){
  def secret = (xml \\ "secret").text
  def accessRequests = Seq((xml \\ "AccessRequests").toString())
}
//abstract class Application {
//	def requests:Seq[Any]
//	def responses:Seq[Any]
//}
//
//class Request{
//  def matchSignal(s:Signal)=true
//}
//
//class Response{
//  
//}