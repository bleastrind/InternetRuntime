import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;


public class GenXmlStringServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GenXmlStringServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String fileName = request.getParameter("fileName");
		System.out.println("fileName"+fileName);
		
		StringBuilder fileUri = new StringBuilder();
		fileUri.append("E://workspace/IfApp/WebRoot/ApplicationXmls/");
		fileUri.append(fileName);
		
		System.out.println("FILEURI"+fileUri.toString());
		
		File file = new File(fileUri.toString());
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temString = null;
			StringBuilder resultString = new StringBuilder();
			while((temString = reader.readLine())!=null){
				resultString.append(temString);
			}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write(resultString.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(reader !=null){
				try {
					reader.close();
				} catch (IOException e1) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
