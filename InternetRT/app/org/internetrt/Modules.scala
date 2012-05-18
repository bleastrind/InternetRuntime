package org.internetrt
import org.internetrt.core.Components
import org.internetrt.core._
import org.internetrt.core.io.IOManagerProductionComponent


/**
 * This object control all the connections in the website 
 */
object SiteInternetRuntime extends InternetRuntime{
	val components = new Components 
		  				with StubSignalSystemComponent
						with StubConfigurationSystemComponent
						with IOManagerProductionComponent
						with StubSecurityPrivacyComponent
						{}
}

object CONSTS{
  val SESSIONUID = "UID";
  val CLIENTID = "CID";
  val MSGID = "msgID";
  
  val CLIENTSTATUS = "CLIENTSTATUS";
  val ANONYMOUS = "Anonymous";
  
  val ACCESSTOKEN = "access_token";
  
}
