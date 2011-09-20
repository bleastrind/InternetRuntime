package cn.org.act.internetos.manage.app;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.persist.Application;

public class ConfigParser {
//	public static void main(String[] args){
//		//parseBooks();
//		String config = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
//		"<Application>"+
//		"<Name>Test</Name>"+
//		"<Listeners>"+
//			"<HttpListener>"+
//					"<URL>http://localhost:8080/DemoApp/listener</URL>"+
//					"<MatchRule type=\"urlregex\">"+
//						".*/signal/send.*"+
//					"</MatchRule>"+
//			"</HttpListener>"+
//		"</Listeners>"+
//	"</Application>";
//		new Application("a",config);
//	}
	public static void Parse(
			Application app,
			UserSpace userspace) {

		try {
			
			Document document =DocumentHelper.parseText(app.getConfig());
			Element application = document.getRootElement();
			
			app.setName(application.elementText("Name"));
			
			//XPath is not avaliable in dom4j of xstream
			//List<Node> listeners = document.selectNodes("/application/listeners");
			
			@SuppressWarnings("unchecked")
			List<Element> listeners = application.element("Listeners").elements();
			
			ListenerFactory factory = new ListenerFactory(userspace);
			
			for(Element node : listeners){
				app.getListeners().add(factory.createListener(node));
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
}
