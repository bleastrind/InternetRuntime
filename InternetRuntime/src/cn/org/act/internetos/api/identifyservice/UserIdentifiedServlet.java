package cn.org.act.internetos.api.identifyservice;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import cn.org.act.internetos.Settings;
import cn.org.act.internetos.UserSpace;

/**
 * Servlet Filter implementation class UserIdentifier
 */
//@WebFilter(dispatcherTypes = {
//				DispatcherType.REQUEST, 
//		}
//					, urlPatterns = { "/*" })
public abstract class UserIdentifiedServlet extends HttpServlet {


	public UserSpace getUserSpace(HttpServletRequest request){
		return UserSpace.getUserSpace(getAccessToken(request));
	}
	
	public String getAccessToken(HttpServletRequest request){
		String token = request.getHeader(Settings.TOKEN);

		token = null == token ? request.getParameter(Settings.TOKEN) : token;
		return token;
	}
}
