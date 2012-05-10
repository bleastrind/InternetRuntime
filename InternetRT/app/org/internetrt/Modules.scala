package org.internetrt
import org.internetrt.core.Components
import org.internetrt.core._


/**
 * This object control all the connections in the website 
 */
object SiteInternetRuntime extends InternetRuntime{
	val components = new Components 
		  				with StubSignalSystemComponent
						with StubConfigurationSystemComponent
						with StubIOManagerComponent
						with StubSecurityPrivacyComponent
						{}
}

object CONSTS{
  val SESSIONUID = "UID";
  val CLIENTID = "CID";
  val CLIENTSTATUS = "CLIENTSTATUS";
  val ANONYMOUS = "Anonymous";
}