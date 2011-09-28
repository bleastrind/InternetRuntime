package org.internetrt.demo.fakeifttt;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Listener
 */
@WebServlet("/Listener")
public class Listener extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Listener() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String usertoken = checkUser(request);
		String responseString = usertoken+"has picked a picture!!!";
		userResponse.put(usertoken, responseString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private static HashMap<String,String> userResponse = new HashMap<String,String>();
	
	public static String checkUser(HttpServletRequest request) {
		String userToken = request.getHeader("token");
		userToken = userToken == null ? request.getParameter("token")
				: userToken;
		return userToken;
	}
	public static String getResponse(String usertoken){
		return userResponse.get(usertoken);
	}
}
