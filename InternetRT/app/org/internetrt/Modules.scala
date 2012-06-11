package org.internetrt
import org.internetrt.core._
import org.internetrt.core.security._
import org.internetrt.persistent._
import org.internetrt.core.io.IOManagerImpl
import org.internetrt.core.configuration.ConfigurationSystem
import org.internetrt.core.configuration.StubConfigurationSystem
import org.internetrt.core.signalsystem.SignalSystemImpl
import org.internetrt.core.signalsystem.workflow.WorkflowEngine
import org.internetrt.core.model.Routing
import org.internetrt.core.model.RoutingInstance
import org.internetrt.core.signalsystem.Signal
import org.internetrt.core.signalsystem.workflow.WorkflowEngineImpl


/**
 * This object control all the connections in the website 
 */
object SiteInternetRuntime extends InternetRuntime{

	object authCenter extends{
	  val global = SiteInternetRuntime.this
	}with MemoryAuthCenter
	
	object signalSystem extends{
	  val global = SiteInternetRuntime.this
	}with MemorySignalSystem
	
	object ioManager extends{
	  val global = SiteInternetRuntime.this 
	}with IOManagerImpl
	
	object confSystem extends StubConfigurationSystem
}

trait MemoryAuthCenter extends AuthCenterImpl{
  object internalUserPool extends StubInternalUserPool
  object accessTokenPool extends StubAccessTokenPool
  object authCodePool extends StubAuthCodePool 
  object appPool extends StubAppPool
}

trait MemorySignalSystem extends SignalSystemImpl{
  object workflowEngine extends WorkflowEngineImpl{
	  object routingInstancePool extends StubRoutingInstancePool
  }
  object routingResourcePool extends MemoryRoutingResourcePool
}

object CONSTS{
  val SESSIONUID = "UID";
  val CLIENTID = "CID";
  val MSGID = "msgID";
  
  val CLIENTSTATUS = "CLIENTSTATUS";
  val ANONYMOUS = "Anonymous";
  
  val ACCESSTOKEN = "access_token";
  
}
