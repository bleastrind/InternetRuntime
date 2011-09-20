package cn.org.act.internetos.manage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.internetos.ModuleConstructor;
import cn.org.act.internetos.Settings;
import cn.org.act.internetos.persist.Application;
import cn.org.act.tools.HttpHelper;

/**
 * Servlet implementation class addApp
 */
@WebServlet("/addApp")
public class addApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addApp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = (String) request.getSession().getAttribute(Settings.TOKEN);
		if(user == null)
			response.sendRedirect("identifyservice/login?callback="+
					HttpHelper.getContextPath(request)+"/addApp");
		else
			getServletContext().getRequestDispatcher("/appinstall.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String config = request.getParameter("config");
		String user = (String) request.getSession().getAttribute(Settings.TOKEN);
		if(user == null)
			//should not happen
			response.sendRedirect("identifyservice/login?callback="+
					HttpHelper.getContextPath(request)+"/addApp");
		else{
		
			ModuleConstructor.getAppDAO().addApp(new Application(user,config));
			response.getWriter().println("Register success");
			//System.out.println("Register success");
			//response.sendRedirect("http://localhost:8080/DemoApp/Init");
		}
	}

}
