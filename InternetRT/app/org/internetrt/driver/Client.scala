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

object Client extends Controller {
  var clients = Map.empty[String, PushEnumerator[String]]

  def sendMessage(user: String, data: String) {
    clients(user).push(data);
  }

  def test = Action {
    implicit val timeout = Timeout(5.seconds)
    ClientMessageActor.ref ! Message("1", "test");
    Ok("D")
  }
  def tt() = {
    Thread.sleep(10000)
    "sfd"
  }
  def longpollingjsonp = Action {
    request =>
      val cid = request.session.get("userid");

      implicit val timeout = Timeout(5.seconds)

      import play.api.Play.current;

      val result = ClientMessageActor.ref ? Join("1") recover{
        case e:AskTimeoutException => "Timeout"
      }
//      val data = Akka.future {
//        try {
//          Await.result(result, timeout.duration)
//        } catch {
//          case e: TimeoutException => "Timeout"
//        }
//      }
      Async {

 //       System.out.println(data)

        result.mapTo[String].asPromise
          .map(i => 
          Ok(request.queryString.get("callback").map(s => s.head).get + "(" + i + ")"))

      }
  }
}

class ClientMessageActor extends Actor {

  var clients = Map.empty[String, ActorRef]

  def receive = {

    case Join(cid) => {
      // lazy val channel: PushEnumerator[String] = Enumerator.imperative[String](
      //   onComplete = self ! Quit(cid))
      clients += (cid -> sender)
      Logger.info("New member joined")
      //Thread.sleep(10000);
      //clients(cid) ! "test"
    }

    case Quit(cid) => {
      Logger.info("Member has disconnected: " + cid)

    }

    case Message(cid, msg) => {
      Logger.info("Got message, send it to " + cid)
      
      clients(cid) ! msg
      clients -= cid;
    }

  }

}

object ClientMessageActor {

  trait Event
  case class Join(cid: String) extends Event
  case class Quit(cid: String) extends Event
  case class Message(cid: String, msg: String) extends Event
  lazy val system = ActorSystem("chatroom")
  lazy val ref = system.actorOf(Props[ClientMessageActor])

}