package cn.org.act.demo.pickapicture;

import javax.servlet.http.HttpServletRequest;

public class Util 
{
	public static String checkUser(HttpServletRequest request) {
		String userToken = request.getHeader("token");
		userToken = userToken == null ? request.getParameter("token")
				: userToken;
				
		return userToken;
	}

}
