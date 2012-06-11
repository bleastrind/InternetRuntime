package org.internetrt.core.io
import akka.dispatch.Future
import org.internetrt.driver.clientmanager.ClientsManager

	
	abstract class IOManagerImpl extends IOManager{
		 val clientsdriver = ClientsManager /**TODO direct ref: 3/3  */
	  
	     def sendToClient(uid:String,msg:String,allowedStatus:Seq[String])={
	       clientsdriver.sendevent(uid,msg,allowedStatus)
	     }
		 
		 /**TODO if timeout ,do it in database*/
	     def readFromClient(uid:String,msg:String,allowedStatus:Seq[String]):Future[String]={
	        clientsdriver.ask(uid,msg,allowedStatus)
	     }
	}


