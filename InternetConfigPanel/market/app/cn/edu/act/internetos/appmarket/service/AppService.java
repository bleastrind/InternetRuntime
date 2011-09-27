package cn.edu.act.internetos.appmarket.service;

import java.util.*;
import models.*;

public class AppService{
	
	public static List<App> getAllApp(User user){		
		UserSpaceDao userspacedao = new UserSpaceDao();
		AppDao appdao = new AppDao();
	    boolean flag; 
		UserSpace userspace = userspacedao.getUserSpace(user);		
 		List<App> applist = new ArrayList<App>();
		for (AppConfig appconfig: userspace.getAppConfigs())
		{
			if(appconfig.appId == null || appconfig.appId.isEmpty() || appdao.findById(appconfig.appId) == null)
				continue;
			flag = false;
			for (App app: applist)
				if ((app.getId().equals(appconfig.appId)) || (app.getName().equals(appdao.findById(appconfig.getAppId()).getName())))
					flag = true;
			if (flag)
				continue;
			applist.add(appdao.findById(appconfig.appId));
		}
		return applist;
	}
	
	public static List<AppConfig> getAllConfig(User user){
		UserSpaceDao userspacedao = new UserSpaceDao();
		UserSpace userspace = userspacedao.getUserSpace(user);	
		return userspace.getAppConfigs();
	
	}
	
	
    public static List<App> getAllApps()  
	{
        AppDao appdao = new AppDao();	
        List<App> applist = appdao.getAllApps();     
        return applist;		  
    }
	
	public static boolean addUserApp(App app, User user)
	{
		UserSpaceDao userspacedao = new UserSpaceDao();	
		UserSpace userspace = userspacedao.getUserSpace(user);
		
		for (AppConfig appconfig: userspace.getAppConfigs())
			if (appconfig.getAppId().equals(app.getId()))
				return false;
		List<AppConfig> temp = userspace.getAppConfigs();
		temp.add(new AppConfig(app.getId(), "TEST CONFIG"));
		userspace.setAppConfigs(temp);		
		userspacedao.save(userspace);
		return true;		
	}
	
	public static void deleteUserApp(App app, User user)
	{
		UserSpaceDao userspacedao = new UserSpaceDao();	
		UserSpace userspace = userspacedao.getUserSpace(user);
		List<AppConfig> temp = userspace.getAppConfigs();	
		//The code above is correct in logic, but leaves a ConcurrentModificationException while testing.
		//What's more, amount many existed apps, the exception only occurs when deleting the last one!
		/*
		for (AppConfig appConfig: userspace.getAppConfigs())
		{
			if (appConfig.getAppId().equals(app.getId()))
			{
				temp.remove(appConfig);
			}
		}
		*/		
		for (int i = 0; i < userspace.getAppConfigs().size(); i++)
		{
			if (userspace.getAppConfigs().get(i).getAppId().equals(app.getId()))
			{
				temp.remove(userspace.getAppConfigs().get(i));
			}
		}		
		userspace.setAppConfigs(temp);
		userspacedao.save(userspace);
	}		
	
	public static void setConfig(String userId, String appId, String config)
	{
		UserSpaceDao userspacedao = new UserSpaceDao();
		UserSpace userspace = userspacedao.getUserSpace(userId);
		List<AppConfig> temp = userspace.getAppConfigs();
		for (AppConfig appConfig: userspace.getAppConfigs())
			if (appConfig.getAppId().equals(appId))
				temp.remove(appConfig);
		temp.add(new AppConfig(appId, config));
		userspace.setAppConfigs(temp);
		userspacedao.save(userspace);		
	}
}