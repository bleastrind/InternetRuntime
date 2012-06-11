package org.internetrt.core.security
import org.internetrt.core.InternetRuntime
import java.util.Date

case class AccessToken(value:String,expire:Date,refresh:String);

	trait AuthCenter{
	  val global:InternetRuntime
  
	  /**
	   * Register new user, return false if user already exists
	   */
	  def register(username:String,password:String):Boolean
	  
	  /**
	   * Login with internal username&password, return the uid
	   * May throw exception if login failed
	   */
	  def login(username:String,password:String):String
	  
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
	   * get userID by accessToken , app should provide it's secret s.t. only it can get the userid by itself's accesstoken
	   */
	  def getUserIDByAccessToken(accessToken:String,appSecret:String):String
	  /**
	   * This part is only for internal use
	   */
	  private[core] def getUserIDAppIDPair(accessToken:String):(String,String)
	  
	}
