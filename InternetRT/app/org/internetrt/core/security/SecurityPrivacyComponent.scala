package org.internetrt.core.security
import org.internetrt.core.InternetRuntime
import java.util.Date

case class AccessToken(value:String,expire:Date,refresh:String)

	trait AuthCenter{
	  val global:InternetRuntime
  
	  /**
	   * Generate an auth code that identify the user is already granted the app the authorization 
	   * 
	   * userID will never be used as part of API for external environment
	   * appID and userID is static 
	   */
	  def genAuthCode(appID:String,userID:String):String
	  
	  /**
	   * A user granted the authorization to all the roles in the workflow 
	   */
	  def genAuthCode(appID:String,appSecret:String,workflowID:String):String
	  /**
	   * code can be auth token 
	   */
	  def genAccessTokenByAuthToken(authtoken:String,appID:String,appSecret:String):AccessToken
	  
	  /**
	   * This part is only for internal use
	   */
	  private[core] def getUserIDAppIDPair(accessToken:String):(String,String)
	  
	}
