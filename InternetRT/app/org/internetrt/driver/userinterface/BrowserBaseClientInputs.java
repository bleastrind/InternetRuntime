package org.internetrt.driver.userinterface;

import org.internetrt.CONSTS;
import org.internetrt.SiteUserInterface;

import play.mvc.Controller;
import play.mvc.Result;

public class BrowserBaseClientInputs extends Controller{

	
	public static Result register(){
		String username = request().queryString().get("username")[0];
		String password = request().queryString().get("password")[0];
		return ok(SiteUserInterface.register(username,password));
	}
	public static Result login(){
		String username = request().queryString().get("username")[0];
		String password = request().queryString().get("password")[0];
		
		try{
			String uid = SiteUserInterface.login(username,password);

			session().put(CONSTS.SESSIONUID(), uid);
			return ok();
		}catch(Exception e){
			return ok(e.getMessage());
		}	
	}
}
