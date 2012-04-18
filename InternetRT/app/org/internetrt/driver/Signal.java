package org.internetrt.driver;

import play.mvc.Result;
import play.mvc.Controller;

public class Signal extends Controller {
	public static Result index(){
		return ok("It works!");
	}
	
	public static Result head(String signalname){
		return ok("d");
	}
	
	public static Result process(String signalname){
		
		return ok(signalname+request().body());
	}
}
