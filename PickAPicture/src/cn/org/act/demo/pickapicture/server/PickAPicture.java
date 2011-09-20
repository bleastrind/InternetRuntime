package cn.org.act.demo.pickapicture.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;



/**
 * Servlet implementation class PickAPicture
 */
@WebServlet(urlPatterns={"/PickAPicture"},asyncSupported=true)
public class PickAPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PickAPicture() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//创建AsyncContext,开始异步调用
		final AsyncContext actx = request.startAsync();
		
		//test
		System.out.println("signal has already arrived PickAPicture servlet");
		
		//启动异步调用的超时时长
		actx.setTimeout(10*60*1000);

		String userToken = request.getHeader("token");
		BlockingQueue<String> messageQueue;
		messageQueue = ManageMessage.prepareMessageQueue(userToken);
//		request.getSession().setAttribute("messageQueue", messageQueue);
		actx.start(new AsyncRequest(actx,messageQueue));
		
		osOpenNewWindow(userToken);
		
		//test
		System.out.println("The pop window has been openned!");
		
		//启动异步调用的线程
//		actx.start();
	}

	private void osOpenNewWindow(String userToken) throws UnsupportedEncodingException,
			IOException, ClientProtocolException {
		HttpClient client = new DefaultHttpClient();
		String signalUrl = "http://localhost:8080/InternetOS/signal/selectPicture";
		HttpPost req = new HttpPost(signalUrl);
		req.addHeader("token",userToken);
		req.addHeader("client-signal", "");
		req.addHeader("clienttype","osclient://internetos/clientsignal/scriptloader");
		req.addHeader("async", "true");
		HttpEntity entity = new StringEntity("window.open('http://localhost:8080/PickAPicture/SelectAPicture.html')");
		req.setEntity(entity);
		client.execute(req);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

class AsyncRequest implements Runnable{

	private AsyncContext aCtx;

	private BlockingQueue<String> messageQueue;
	public AsyncRequest(AsyncContext aCtx,BlockingQueue<String> messageQueue) {
		this.aCtx = aCtx;
		this.messageQueue = messageQueue ;
	}

	@Override
	public void run() {
		
		String cMessage = null;  
        try {  
            cMessage = messageQueue.take();  

            try {  
                PrintWriter acWriter = aCtx.getResponse().getWriter();  
                acWriter.println(cMessage);  
                acWriter.flush();  
                aCtx.complete();
            } catch(Exception ex) {  
                System.out.println(ex);  
            }  

        } catch(InterruptedException iex) {  
        	 
        }  
	}
	
}

