

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accessToken.Application;
import accessToken.GetAppsByAT;
import accessToken.Signal;
import accessToken.ToJson;

/**
 * Servlet implementation class accessTokenServlet
 */
@WebServlet("/accessTokenServlet")
public class accessTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public accessTokenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String accessToken = request.getParameter("accessToken");
		GetAppsByAT getAppsByAT = new GetAppsByAT(accessToken);
//		ArrayList<Signal> userSignals = getAppsByAT.getUserSignals();
//		ArrayList<String> userSigName = new ArrayList();
//		for(int i = 0; i<userSignals.size(); i++)
//		{
//			userSigName.add(userSignals.get(i).getSignalName());
//		}
//		
//		userSigName.add("/signal/storage");
//		userSigName.add("/signal/open");
		
		ArrayList<Application> apps = getAppsByAT.getApplications();
		
		ToJson toJson = new ToJson();
		String result = toJson.AppsToJSon(apps);
		
		System.out.println(result);
		
		response.getWriter().write(result);
		response.getWriter().flush();
	}

}
