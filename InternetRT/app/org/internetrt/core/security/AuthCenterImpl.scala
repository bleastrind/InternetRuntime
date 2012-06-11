package org.internetrt.core.security
import org.internetrt.persistent.{AccessTokenPool,AuthCodePool,AppPool}
import java.util.UUID
import java.util.Date
import org.internetrt.exceptions._
import org.internetrt.persistent.InternalUserPool
import org.internetrt.util.Encrypt
import org.internetrt.core.I18n


abstract class AuthCenterImpl extends AnyRef 
  with AuthCenter{
	
  import global.signalSystem
  
  val internalUserPool:InternalUserPool
  val accessTokenPool:AccessTokenPool
  val authCodePool:AuthCodePool
  val appPool:AppPool
  
  def register(username:String,password:String)={
    internalUserPool.get(username) match{
      case Some(_)=> false
      case None=> {
        val uid = UUID.randomUUID().toString();
        internalUserPool.put(username,(uid,Encrypt.encrypt(password)))
        true
      }
    }
  }
  
  def login(username:String,password:String):String={
    internalUserPool.get(username) match{
      case Some((uid,encryptedpassword))=>{
        if (encryptedpassword == Encrypt.encrypt(password))
          uid
        else
          throw new WrongPasswordException()
      }
      case _ => throw new UserNotRegisteredException()
    }
  }
  
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
     val routingInstance = signalSystem.getRoutingInstaceByworkflowID(workflowID) match{
       case Some(ins) => ins
       case _ => throw new AuthDelayException(I18n.REJECT)
     };
     val userID = routingInstance.userID;
     
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
        val time = new Date()
        time.setTime(time.getTime()+10000) //expire time
        val accessToken = AccessToken(UUID.randomUUID().toString(),time ,null)

        accessTokenPool.put(accessToken.value,(accessToken,appID,userID))
        accessToken
      }
      case None => null
    }
  }
  
  def getUserIDByAccessToken(accessToken:String,appSecret:String):String = {
	  accessTokenPool.get(accessToken) match{
      case Some((token,appID,userID))=>{
          checkApp(appID, appSecret)
          userID
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
          (userID,appID)
      }
      case None => null
    }
  }
}