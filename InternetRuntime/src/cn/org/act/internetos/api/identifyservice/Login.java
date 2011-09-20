package cn.org.act.internetos.api.identifyservice;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.internetos.ModuleConstructor;
import cn.org.act.internetos.Settings;

/**
 * Servlet implementation class Login
 */
@WebServlet("/identifyservice/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setAttribute("callback", request.getParameter("callback"));
		String sessionAccessToken = (String) request.getSession().getAttribute(Settings.TOKEN);
		if(sessionAccessToken!= null)
			returnToCallback(request,response,ModuleConstructor.getIdentifyService().getAuthToken(sessionAccessToken));
		else
			getServletContext().getRequestDispatcher("/gettoken.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String authtoken = ModuleConstructor.getIdentifyService().getAuthToken(
				request.getParameter("username"),request.getParameter("password")
		);
		
		//login
		request.getSession().setAttribute(Settings.TOKEN, ModuleConstructor.getIdentifyService().getAccessToken(authtoken));
		
		returnToCallback(request,response,authtoken);
	}
	
	private void returnToCallback(HttpServletRequest request, HttpServletResponse response,String authtoken) throws IOException{
		String callback = request.getParameter("callback");

		response.sendRedirect(callback + "?"+
				Settings.AUTHTOKEN + "=" + authtoken);
	}

}
