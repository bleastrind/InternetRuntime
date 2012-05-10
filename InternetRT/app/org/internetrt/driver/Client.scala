package org.internetrt.driver
import play.api.mvc._
import play.api.libs.iteratee.Enumerator
import play.api.libs.Comet
import play.api.libs.iteratee.PushEnumerator
import play.api._
import akka.actor._
import akka.actor.Actor._
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import akka.util.duration._
import play.api._
import play.api.mvc._
import play.api.libs._
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import akka.util.Timeout
import akka.pattern.ask
import ClientMessageActor._
import akka.dispatch.Await
import java.util.concurrent.TimeoutException
import akka.pattern.AskTimeoutException
import java.util.UUID
import scala.collection.mutable.ListBuffer
import org.internetrt.driver.clientmanager.ClientDriver
import org.internetrt.CONSTS
import org.internetrt.driver.clientmanager.ClientsManager
import org.internetrt.driver.clientmanager.ClientStatus

object Client extends Controller {
  var clients = Map.empty[String, PushEnumerator[String]]

  def sendMessage(user: String, data: String) {
    clients(user).push(data);
  }

  def test = Action {
    request=>
    val uid = request.session.get(CONSTS.SESSIONUID).getOrElse(CONSTS.ANONYMOUS);
    
    implicit val timeout = Timeout(5.seconds)
    ClientMessageActor.ref ! Message(uid, "test");
    Ok("D")
  }
  def tt() = {
    Thread.sleep(10000)
    "sfd"
  }
  def longpollingjsonp = Action {
    request =>
      val uid = request.session.get(CONSTS.SESSIONUID).getOrElse(CONSTS.ANONYMOUS);
      val cid = request.queryString.get(CONSTS.CLIENTID) match {
        case Some(x::rest)=>x //get the first client id
        case None=> null
      }
      val status = request.queryString.get(CONSTS.CLIENTSTATUS) match{
        case Some(x::rest)=>x //get the first client id
        case None=> ClientStatus.Active.toString()
      }
      
      implicit val timeout = Timeout(5.seconds)

      import play.api.Play.current;

      val result = ClientMessageActor.ref ? Join(uid, cid ,status) recover{
        case e:AskTimeoutException => {
          ClientMessageActor.ref ! Quit()
          "Timeout:"+cid // The client script can request with the cid next time s.t. it can set the status of the channel
        }
      }

      Async {
        result.mapTo[String].asPromise
          .map(i => 
          Ok(request.queryString.get("callback").map(s => s.head).get + "(" + i + ")"))
      }
  }
}
class PageJavaScriptSlimClientDriver(cid:String) extends ClientDriver{
	var channel:ActorRef = null
	
	def response(data:String){
	  channel ! data
	}
}

class ClientMessageActor extends Actor {
	import scala.collection.mutable
	import ClientStatus._

  val pagedrivers = mutable.Map.empty[String,PageJavaScriptSlimClientDriver]
  

  def receive = {

    case Join(uid,cid,clientStatus) => {
        
    	//get the unique channel
      val clientDriver = pagedrivers.get(cid) match{
        case Some(c)=> c
        case None=>{
           //create new one
          val newcid = UUID.randomUUID().toString()
    	  val c = new PageJavaScriptSlimClientDriver(newcid)
    	  pagedrivers += (newcid -> c)
    	  
    	  //register the channel as one of the users("uid")
    	  clientsManager.join(uid,c)
    	  
    	  c // return the newly created driver         
        }
      }
       //reset the actor to response
        /**TODO CHECK IF THIS IS CHANGED IN CLIENTSMANAGER's USERCONNECTOR*/
    	clientDriver.channel = sender
      
    	/**TODO CHECK IF THIS IS CHANGED IN CLIENTSMANAGER's USERCONNECTOR*/
    	//reset the status of the channel
    	clientDriver.clientstatus = clientStatus;
    	
      Logger.info("New member joined:"+sender)
      Logger.info("size:"+pagedrivers.size)

    }

    case Quit() => {
      Logger.info("Member has disconnected: "+sender)
      
      //TODO how to find the actually changed actor?
      for(p <- pagedrivers){
        if (p._2.channel.isTerminated){
          if(p._2.clientstatus == ClientStatus.WaitingHeartBeat.toString()){
            p._2.onClientDistory(p._2)
            p._2.clientstatus = ClientStatus.Dead.toString()
          }
          else 
        	p._2.clientstatus = ClientStatus.WaitingHeartBeat.toString()
        }
      }
      val terminated = pagedrivers.filter(p=>p._2.clientstatus == ClientStatus.Dead.toString()).map(p=>p._1)
      pagedrivers --= terminated;
    }

    case Message(uid, msg) => {
      Logger.info("Got message, send it to:" + uid)
      clientsManager.write(uid, msg, Seq(ClientStatus.Active.toString()))
    }

  }

}

object ClientMessageActor {

  trait Event
  case class Join(uid:String,cid:String,clientstatus:String) extends Event
  case class Quit() extends Event
  case class Message(uid:String, msg: String) extends Event
  lazy val system = ActorSystem("chatroom")
  lazy val ref = system.actorOf(Props[ClientMessageActor])
  
  val clientsManager = new ClientsManager()
}