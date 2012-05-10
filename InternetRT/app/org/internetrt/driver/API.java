package org.internetrt.driver;

import net.sf.ehcache.search.Results;

import org.internetrt.*;
import org.internetrt.core.security.AccessToken;
import org.internetrt.core.signalsystem.SignalResponse;

import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Controller;

public class API extends Controller {
	/**
	 * This api used to query the workflow the signal will invoke
	 * but won't change the state of the workflow
	 * @param signalname
	 * @return
	 */
	public static Result head(String signalname){
		SignalResponse res = SiteInternetRuntime.getHeadResponse(new HttpGetSignal(request()));
		
		HttpResponseProcessor processor = new HttpResponseProcessor(res);
		return processor.generateResult();
	}
	
	/**
	 * The API for Web Server Flow
	 * @param appID
	 * @param response_type
	 * @param redirect_uri
	 * @return
	 */
	public static Result authorize(String appID,String response_type,String redirect_uri){
		String userID = session(CONSTS.SESSIONUID());
		String code= SiteInternetRuntime.getAuthcodeForServerFlow(appID,userID,redirect_uri);
		return redirect(redirect_uri+"?code="+code);
	}
	/**
	 * The API for global workflow type auth
	 * @param appSecret
	 * @param workflowID
	 * @return
	 */
	public static Result authorize(String appSecret, String workflowID){
		return ok("{code:2}");
	}
	
	public static Result token(String authtoken,String appID,String appSecret){
		AccessToken accesstoken = SiteInternetRuntime.getAccessTokenByAuthtoken(appID, authtoken, appSecret);
		return ok("{access_token:\""+accesstoken.value()+
				"\"\nexpires_in: "+accesstoken.expire()+
				"\nrefresh_token=\""+accesstoken.refresh()+"\"}");
	}
}
