package cn.org.act.internetos.persist;

import java.util.ArrayList;
import java.util.List;

import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.manage.app.ConfigParser;
import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.SignalListener;

public class Application {
	private String user;
	private String name;
	private String config;
	private ArrayList<SignalListener> listeners;
	
	
	public Application(String user,String config) {
		this.setUser(user);
		this.setConfig(config.trim());
		//config = config.replace("\r\n", "");
		listeners = new ArrayList<SignalListener>();
//		ConfigParser.Parse(this);
	}
	
	private boolean isInited = false;
	public void init(UserSpace userspace){
		ConfigParser.Parse(this, userspace);
		isInited = true;
	}
	public boolean isInited(){
		return isInited;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getConfig() {
		return config;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public List<SignalListener> getListeners() {
		return listeners;
	}
	
}
