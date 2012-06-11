package accessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Application
{
	private  Document doc = null;
	private String appName = null;
	private long appId = 0;
	private  ArrayList<Signal> signals = new ArrayList();
	
	public Application(Document param)
	{
		this.doc = param;
		resolving();
	}
	
	private void resolving()
	{
		Element root = doc.getRootElement();			//根节点
		Iterator itRoot = root.elementIterator();
			
		Element second;										//二级节点
		Iterator itSec;
			
		while(itRoot.hasNext())
		{
			second = (Element) itRoot.next();
			if(second.getName()=="Name")
			{
				appName = second.getText();
			}
			else if(second.getName()=="Signals")
			{
				Element third;
				Iterator itThird;
				itSec = second.elementIterator();
				
				while(itSec.hasNext())
				{
					third = (Element)itSec.next();
					itThird = third.elementIterator();
						
					Element fourth;
					while(itThird.hasNext())
					{
						Signal signal = new Signal();
						fourth = (Element)itThird.next();
						if(fourth.getName()=="Signalname")
						{
							String text1 = fourth.getText();
							 signal.setSignalName(text1);
//							System.out.println(text1);
						}
						fourth = (Element)itThird.next();
						 if(fourth.getName()=="Description")
						{
							String text2	 = fourth.getText();
//							System.out.println(text2);
							signal.setDescription(text2);
						}
						 signals.add(signal);
					}
				}
			}	
		}
	}

	public String getAppName() {
		return appName;
	}

	public ArrayList<Signal> getSignals() {
		return signals;
	}


}