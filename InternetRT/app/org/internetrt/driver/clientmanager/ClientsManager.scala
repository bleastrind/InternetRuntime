package org.internetrt.driver.clientmanager

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
  def response(data:String)
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
	   def write(uid:String,msg:String,allowedStatus:Seq[String]){
	     val connector = clients.get(uid).get;
	     connector.write(msg,allowedStatus);
	   }
}	
class UserConnector(uid:String){
import scala.collection.mutable.ListBuffer
	  val clients = ListBuffer.empty[ClientDriver];
	  
	  def register(client:ClientDriver){
	    clients += client;
	  }
	  def unregister(client:ClientDriver){
	    clients -= client;
	  }
	  
	  def write(msg:String,allowedStatus:Seq[String]){
	    for(c <- clients){
	      if(allowedStatus.contains(c.clientstatus))
	        c.response(msg)
	    }
	  }
	}