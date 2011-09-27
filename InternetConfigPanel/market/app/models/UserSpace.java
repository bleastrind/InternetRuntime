package models;
import java.io.*;
import java.util.List;
import java.util.*;
/*
 * UserSapce is a class of UserSpace and for UserSpaceDao;
 */

public class UserSpace {
	//Fields	
	private String id;
    private List<AppConfig> appConfigs;

    //Constructors  
	public UserSpace(String id){
		this.id = id;
		appConfigs = new ArrayList<AppConfig>();
	}
	

	public UserSpace(String id, List<AppConfig> appConfigs){
		this.id = id;
		this.appConfigs = appConfigs;
	}
	
	//Property Accessors	
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}	
	public List<AppConfig> getAppConfigs(){
		return this.appConfigs;
	}
	public void setAppConfigs(List<AppConfig> appConfigs){
		this.appConfigs = appConfigs;
	}
	
}
