package accessToken;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class ToJson {
//	private String userName = null;
//	private JSONArray triggers = new JSONArray() ;
//	private JSONArray triggerChannels = new JSONArray() ;
//	private JSONArray actionChannels = new JSONArray() ;
//	private String finalResult = null;
	
	public String ArrayListToJSon (String arrayKey, ArrayList array)
	{
		String result = null;
		JSONObject o = new JSONObject();
		try {
			o.put(arrayKey, array);
			result = o.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String AppsToJSon(ArrayList<Application> apps)
	{
		String result = null;
		JSONObject appsOb = new JSONObject();
		JSONArray appsArray = new JSONArray();
		try {
//			o.put("AppName", apps.getAppName());
//			o.put("Signals", apps.getSignals());
			for(int i = 0; i<apps.size(); i++)
			{
				JSONObject o = new JSONObject();
				Application app = apps.get(i);
				o.put("appName", app.getAppName());
				
				ArrayList<Signal> signals = app.getSignals();
				ArrayList<String> signalsName = new ArrayList();
				
				for(int j = 0; j<signals.size();j++)
				{
					signalsName.add(signals.get(j).getSignalName());
				}
				o.put("signals", signalsName);
				appsArray.put(o);
			}
			appsOb.put("apps", appsArray);
			result = appsOb.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
//	public ToJson(String name)
//	{
//		this.userName = name;
//		this.triggers.put("share");
//		this.triggers.put("storage");
//		this.triggers.put("open");
//		
//		this.triggerChannels.put("renren");
//		this.triggerChannels.put("sina");
//		this.triggerChannels.put("tencent");
//		
//		this.actionChannels.put("renren");
//		this.actionChannels.put("sina");
//		this.actionChannels.put("tencent");
//		
//		JSONObject o = new JSONObject();
//		try {
//			o.put("triggers", triggers);
//			o.put("triggerChannels", triggerChannels);
//			o.put("actionChannels", actionChannels);			
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//				 
//		
//		finalResult = o.toString();
//		
//		System.out.println(finalResult);
//	}
//
//	public String getFinalResult() {
//		return finalResult;
//	}
	
	//http://hi.baidu.com/coolcooldool/blog/item/a737888fdc485cf0513d9287.html
}
