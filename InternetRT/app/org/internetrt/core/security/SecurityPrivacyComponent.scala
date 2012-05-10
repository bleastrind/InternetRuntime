package org.internetrt.core.security

case class AccessToken(value:String,expire:Int,refresh:String)
trait SecurityPrivacyComponent {
	val authCenter:AuthCenter
	

	trait AuthCenter{
	  /**
	   * userID will never be used as part of API for external environment
	   * appID and userID is static 
	   */
	  def getAuthCode(appID:String,userID:String):String
	  
	  /**
	   * code can be auth token 
	   */
	  def genAccessTokenByAuthToken(authtoken:String,appID:String,appSecret:String):AccessToken
	  
	  /**
	   * This part is only for internal use
	   */
	  private[core] def getUserIDAppIDPair(accessToken:String):(String,String)
	  
	}
}