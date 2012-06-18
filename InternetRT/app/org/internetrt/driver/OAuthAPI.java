package org.internetrt.driver;


import org.internetrt.*;
import org.internetrt.core.security.*;
import org.internetrt.core.signalsystem.SignalResponse;

import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Controller;

public class OAuthAPI extends Controller {
	
	/**
	 * The API for Web Server Flow
	 * @param appID
	 * @param response_type
	 * @param redirect_uri
	 * @return
	 */
	public static Result authorize(){
		String userID = session(CONSTS.SESSIONUID());
		
		String appID = request().queryString().get("appID")[0];
		String redirect_uri = request().queryString().get("redirect_uri")[0];
		String code= org.internetrt.SiteInternetRuntime.getAuthcodeForServerFlow(appID,userID,redirect_uri);
		return redirect(redirect_uri+"?code="+code);
	}
	/**
	 * The API for global workflow type auth
	 * @param appSecret
	 * @param workflowID
	 * @return
	 */
	public static Result authorize(String appID, String appSecret, String workflowID){
		String code = SiteInternetRuntime.getAuthcodeForActionFlow(appID, appSecret,workflowID);
		return ok("{code:"+code+"}");
	}
	
	public static Result token(String authtoken,String appID,String appSecret){
		AccessToken accesstoken = SiteInternetRuntime.getAccessTokenByAuthtoken(appID, authtoken, appSecret);
		return ok("{access_token:\""+accesstoken.value()+
				"\"\nexpires_in: "+accesstoken.expire()+
				"\nrefresh_token=\""+accesstoken.refresh()+"\"}");
	}
	
	
}
