package cn.org.act.internetos.activities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.signal.Signal;
import cn.org.act.internetos.signal.SignalListener;

public class DelaySignal extends Signal {

	private UserSpace userspace;
	private Signal signal;

	public void setHeaders(Map<String,String> headers) {
		this.signal.setHeaders(headers);
	}
	public Map<String,String> getHeaders() {
		return this.signal.getHeaders();
	}
	public void setMethod(String method) {
		this.signal.setMethod(method);
	}
	public String getMethod() {
		return this.signal.getMethod();
	}
	public void setData(InputStream data){
		this.signal.setData(data);
	}
	public InputStream getData() throws IOException {
		return this.signal.getData();
	}
	public void setUrl(String url) {
		this.signal.setUrl(url);
	}
	public String getUrl() {
		return this.signal.getUrl();
	}
	public DelaySignal(UserSpace userspace,Signal signal){
		this.userspace = userspace;
		this.signal = signal;
		
		
		
		this.signal.makeDataRereadable();
	}
	@Override
	public void sendTo(List<SignalListener> listeners, OutputStream result)
			throws IOException {
		List<SignalListener> readyListeners = new ArrayList<SignalListener>();
		List<SignalListener> notreadyListeners = new ArrayList<SignalListener>();
		
		for(SignalListener listener:listeners){
			if(listener.isEventRecieveReady(userspace))
				readyListeners.add(listener);
			else
				notreadyListeners.add(listener);
		}
		signal.sendTo(readyListeners, result);
		userspace.getActivityManager().registerDelaySignal(this, notreadyListeners);
	}

}
