package cn.org.act.internetos.identify;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.internetos.Settings;

public class IdentifyService{
    /**
     * Default constructor. 
     */
    public IdentifyService() {
        // TODO Auto-generated constructor stub
    }
    
    private static HashMap<String,String> auth2access = new HashMap<String,String>();

    
    public String getAuthToken(String accessToken){
    	auth2access.put("tempauthToken"+accessToken, accessToken);
    	return "tempauthToken"+accessToken;
    }
    
    public String getAuthToken(String username,String password){
    	auth2access.put("token"+username, "accesstoken:"+username);
    	return "token"+username;
    }
    
    public String getAccessToken(String authToken){
    	
    	return auth2access.get(authToken);
    }
}