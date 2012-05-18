package org.internetrt.driver.clientmanager
import java.util.UUID
import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.ActorSystem

/**
 * TODO use enum
 */
  object ClientStatus extends Enumeration{
    type Status = Value
    val Active = Value("Active")
    val Background = Value("Background")
    val Sleep = Value("Sleep")
    val Dead = Value("Dead")
    val WaitingHeartBeat = Value("waiting")
  }
trait ClientDriver{

  
  var onClientDistory:ClientDriver=>Unit = null;
  var clientstatus:String = ClientStatus.Active.toString();
  def response(data:String,msgID:Option[String]=None)

}

class ClientsManager {
  import scala.collection.mutable.Map
 val clients:Map[String,UserConnector]=Map.empty
	  
	  def join(uid:String,driver:ClientDriver){
	    val connector = clients.get(uid) match{
	      case Some(c)=>c
	      case None=>{
	        val c = new UserConnector(uid)
	        clients += (uid->c)
	        c
	      }
	    };
	    
	    connector.register(driver);
	    driver.onClientDistory = connector.unregister;
	  }
  
  		def input(uid:String,msg:String,msgID:Option[String]=None) = {
  		  val connector = clients.get(uid).get;
	      connector.input(msg,msgID);
  		}
	   def sendevent(uid:String,msg:String,allowedStatus:Seq[String]){
	     val connector = clients.get(uid).get;
	     connector.output(msg,allowedStatus);
	   }
//	   def ask(uid:String,msg:String,allowedStatus:Seq[String]) = {
//	     val connector = clients.get(uid).get;
//	     connector.ask(msg,allowedStatus);
//	   }
}	
class UserConnector(uid:String){
import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.Map;
import scala.collection.Seq;

	  val clients = ListBuffer.empty[ClientDriver];
	  
      /**TODO multi thread sync &  confliction*/
      //val waitingMessages = Map.empty[String,ActorRef]; 
	  
	  def register(client:ClientDriver){
	    clients += client;
	  }
	  def unregister(client:ClientDriver){
	    clients -= client;
	  }
	  def input(msg:String,msgid:Option[String])={
	    msgid match{
//	      case Some(id)=> waitingMessages.get(id) match {
//	        case Some(actor) => actor ! msg
//	      }
	      case None => System.out.println(msg) /** This is meanless to real request, Since it should request api directly */
	    }
	  }
//	  def ask(msg:String,allowedStatus:Seq[String])={
//	    val msgID = UUID.randomUUID().toString()
//	    val actor = 
//	    waitingMessages += (msgID -> .sys;
//	    output(msg,allowedStatus,Some(msgID));
//	  }
	  def output(msg:String,allowedStatus:Seq[String],msgID:Option[String]=None){
	    for(c <- clients){
	      if(allowedStatus.contains(c.clientstatus))
	        c.response(msg,msgID)
	    }
	  }
	}