package org.internetrt.core.security
import org.internetrt.persistent.{AccessTokenPool,AuthCodePool,AppPool}
import java.util.UUID
import java.util.Date
import org.internetrt.exceptions.AuthDelayException

abstract class AuthCenterImpl extends AnyRef 
  with AuthCenter{
	
  import global.signalSystem
  
  val accessTokenPool:AccessTokenPool
  val authCodePool:AuthCodePool
  val appPool:AppPool
  
  def checkApp(appID:String,appSecret:String):Unit={
    if( appPool.getAppSecretByID(appID) != appSecret )
      throw new AuthDelayException()
  }
  
  def genAuthCode(appID:String,userID:String):String = {
    val code = UUID.randomUUID().toString();
    authCodePool.put(code,(appID,userID));
    code
  }
  def genAuthCode(appID:String,appSecret:String,workflowID:String):String={
     val routingInstance  = signalSystem.getRoutingInstaceByworkflowID(workflowID);
     val userID = routingInstance.getCurrentUser
     
     checkApp(appID,appSecret)
     
     genAuthCode(appID,userID)
  }
	  /**
	   * code can be auth token 
	   */
  def genAccessTokenByAuthToken(authtoken:String,appID:String,appSecret:String):AccessToken ={
    
    //Make sure the app send the request it self
    checkApp(appID, appSecret)
    
    authCodePool.get(authtoken) match{
      case Some((appID,userID)) =>{
        
        val accessToken = AccessToken(UUID.randomUUID().toString(),new Date(),null)

        accessTokenPool.put(accessToken.value,(accessToken,appID,userID))
        accessToken
      }
      case None => null
    }
  }
	  
	  /**
	   * This part is only for internal use
	   */
  protected[core] def getUserIDAppIDPair(accessToken:String):(String,String) ={

    accessTokenPool.get(accessToken) match{
      case Some((token,appID,userID))=>{
          (appID,userID)
      }
      case None => null
    }
  }
}