package accessToken;

import java.util.ArrayList;
import java.util.Collections;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class GetAppsByAT {

	private ArrayList<String> appsName;
	private ArrayList<Application>applications;
	private ArrayList<Signal> signals;
	
	public GetAppsByAT(String accessToken) {
		// TODO Auto-generated constructor stub
		applications = new ArrayList<Application>();
		readerXML();
	}

	private  void readerXML()
	{  
		try {
			Document doc;
			Application app;
			SAXReader reader = new SAXReader();
 			doc = reader.read("D://workspace/IfApp/WebContent/ApplicationXmls/renrenApplication.xml");
			app = new Application(doc);
			applications.add(app);
			doc = reader.read("D://workspace/IfApp/WebContent/ApplicationXmls/sinaApplication.xml");
			app = new Application(doc);
			applications.add(app);
			doc = reader.read("D://workspace/IfApp/WebContent/ApplicationXmls/tencentApplication.xml");
			app = new Application(doc);
			applications.add(app);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Signal> getUserSignals()
	{
		ArrayList<Signal> userSignals = new ArrayList();
		for(int i = 0; i<applications.size(); i++)
		{
			userSignals.addAll(applications.get(i).getSignals());
		}
		
		//ШЅжи
		for(int i = 0; i<userSignals.size();i++)
		{
			String temp1 = userSignals.get(i).getSignalName();
			for(int j = i+1; j<userSignals.size();j++)
			{
				String temp2 = userSignals.get(j).getSignalName();
				if(temp1.equals(temp2))
				{
					userSignals.remove(j);
					j--;
				}
			}
		}
		
		return userSignals;
	}
	
	public ArrayList<String> getAppsName() {
		return appsName;
	}

	public void setAppsName(ArrayList<String> appsName) {
		this.appsName = appsName;
	}

	public ArrayList<Application> getApplications() {
		return applications;
	}

	public void setApplications(ArrayList<Application> applications) {
		this.applications = applications;
	}
	
	
}
