package cn.org.act.internetos.manage.app;

import org.dom4j.Element;

import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.signal.ClientSignalListener;
import cn.org.act.internetos.signal.HttpSignalListener;
import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.SignalListener;

public class ListenerFactory {

	private UserSpace userspace;
	
	public ListenerFactory(UserSpace userspace){
		this.userspace = userspace;
	}
	
	public SignalListener createListener(Element listener) {
		String listenertype = listener.getName();
		if ("HttpListener".equals(listenertype))
			return createHttpListener(listener);
		else if("ClientListener".equals(listenertype))
			return createClientListener(listener);
		else
			return null;
	}
	private MatchRule getMatchRule(Element listenerElement){
		MatchRule rule = MatchRuleFactory.createMatchRule(listenerElement
				.element("MatchRule"));
		return rule;
	}
	private SignalListener createClientListener(Element listenerElement) {
		String clienttype = listenerElement.elementText("ClientType");
		
		return new ClientSignalListener(userspace,clienttype,getMatchRule(listenerElement));
	}

	private SignalListener createHttpListener(Element listenerElement) {
		String url = listenerElement.elementText("URL");
		
		return new HttpSignalListener(url,getMatchRule(listenerElement));
	}

}
