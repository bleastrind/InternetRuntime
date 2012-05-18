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
	
	public static scala.Tuple2<String,String> getUserAndFrom(Request req){
		String[] accesstokens = req.queryString().get(CONSTS.ACCESSTOKEN());
		String accesstoken;
		if(accesstokens.length == 1)
			accesstoken =accesstokens[0];
		else if(req.cookies().get(CONSTS.ACCESSTOKEN()) != null)
			accesstoken = req.cookies().get(CONSTS.ACCESSTOKEN()).value();
		else
			return new scala.Tuple2<String,String>(CONSTS.ANONYMOUS(),"*");
		return SiteInternetRuntime.getUserAndFromByAccesstoken(accesstoken);
	}
}

class HttpGetSignal implements org.internetrt.core.signalsystem.Signal{
	Request data;
	private final String user;
	private final String from;
	private final String uri;
	
	public HttpGetSignal(Request req){
		this.data = req;
		scala.Tuple2<String,String> userandfrom = Signal.getUserAndFrom(req);
		user = userandfrom._1;
		from = userandfrom._2;
		uri = req.uri();
	}
	
	@Override
	public String user(){
		return user;
	}
	
	@Override
	public String from(){
		return from;
	}
	@Override
	public String uri(){
		return uri;
	}


}