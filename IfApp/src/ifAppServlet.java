

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ifAppServlet
 */
@WebServlet("/ifAppServlet")
public class ifAppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ifAppServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String trigger = request.getParameter("trigger");
		String triggerChannel = request.getParameter("triggerChannel");
		String actionChannel = request.getParameter("actionChannel");
		
		System.out.println("TRIGGER: "+trigger);
		System.out.println("TRIGGERCHANNEL"+triggerChannel);
		System.out.println("ACTIONCHANNEL"+actionChannel);
	}

}
