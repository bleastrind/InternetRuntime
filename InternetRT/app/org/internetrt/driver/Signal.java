package org.internetrt.driver;

import org.internetrt.*;
import org.internetrt.core.signalsystem.SignalResponse;

import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Controller;

public class Signal extends Controller {
	
	public static Result index(){
		return ok("It works!");
	}
	

	/**
	 * Executing a http get signal to run the workflow
	 * @param signalname
	 * @return
	 */
	public static Result process(String signalname){
		SignalResponse res =  SiteInternetRuntime.executeSignal(new HttpGetSignal(request()));
		
		HttpResponseProcessor processor = new HttpResponseProcessor(res);
		return processor.generateResult();
	}
}

class HttpGetSignal implements org.internetrt.core.signalsystem.Signal{
	Request data;
	
	public HttpGetSignal(Request req){
		this.data = req;
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return "Http";
	}
}