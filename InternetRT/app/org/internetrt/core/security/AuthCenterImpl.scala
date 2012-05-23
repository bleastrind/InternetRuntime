package org.internetrt.core.security
import org.internetrt.persistent.{AccessTokenPool,AuthCodePool,AppPool}
import java.util.UUID
import java.util.Date

abstract class AuthCenterImpl extends AnyRef 
  with AuthCenter{
	
  import global.signalSystem
  
  val accessTokenPool:AccessTokenPool
  val authCodePool:AuthCodePool
  val appPool:AppPool
  
  def checkApp(appID:String,appSecret:String)={
    appPool.getAppSecretByID(appID) eq appSecret
  }
  
  def genAuthCode(appID:String,userID:String):String = {
    val code = UUID.randomUUID().toString();
    authCodePool.put(code,(appID,userID));
    code
  }
  def genAuthCode(appID:String,appSecret:String,workflowID:String):String={
     val routingInstance  = signalSystem.getRoutingInstaceByworkflowID(workflowID);
     val userID = routingInstance.getCurrentUser
     if (!checkApp(appID,appSecret))
       return null
     genAuthCode(appID,userID)
  }
	  /**
	   * code can be auth token 
	   */
  def genAccessTokenByAuthToken(authtoken:String,appID:String,appSecret:String):AccessToken ={
    
    //Make sure the app send the request it self
    if( !checkApp(appID, appSecret))
      return null
      
    authCodePool.get(authtoken) match{
      case Some((appID,userID)) =>{
        
        val accessToken = AccessToken(UUID.randomUUID().toString(),new Date(),null)
        accessTokenPool.put(accessToken.value,(accessToken,appID,userID))
        accessToken
      }
    }
  }
	  
	  /**
	   * This part is only for internal use
	   */
  private[core] def getUserIDAppIDPair(accessToken:String):(String,String) ={
    accessTokenPool.get(accessToken) match{
      case Some((token,appID,userID))=>{
          (appID,userID)
      }
      case None => null
    }
  }
}