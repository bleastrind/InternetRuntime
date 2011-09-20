package cn.org.act.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebClient {
	private IHttpModify modifier;
	
	public WebClient(){
		this.modifier = new IHttpModify(){

			@Override
			public void handle(HttpURLConnection conn) {
				// TODO Auto-generated method stub
				
			}};
	}
	public WebClient(IHttpModify modifier){
		this.modifier = modifier;
	}
	
	public void externalForward(String urlString,HttpServletRequest req,HttpServletResponse res) throws IOException{
		ArrayList<String> l = new ArrayList<String>();
		l.add(urlString);
		externalForward(l,req,res);
	}
	/**
	 * only return the first urls' result
	 * request can be used only once!
	 **/
	public void externalForward(Iterable<String> urlStrings,HttpServletRequest req,HttpServletResponse res) throws IOException{

		List<HttpURLConnection> conns = createHttpConnections(urlStrings);
		
		//Method
		String method = req.getMethod();
		
		for(HttpURLConnection conn:conns){
			conn.setRequestMethod( method );
			//!!! GET doesn't need this
			conn.setDoOutput(true);
		}
		
		//header
		Enumeration<String> enumeration = req.getHeaderNames();
		while(enumeration.hasMoreElements()){
			String k = enumeration.nextElement();
	
			for(HttpURLConnection conn:conns){
				conn.addRequestProperty(k, req.getHeader(k));
			}
			
		}

		//get result
		for(HttpURLConnection conn:conns){
			conn.connect();
		}
		
		//request data
		copyStream(req.getInputStream(),getOutputStreams(conns));
		
		//!!! only first
		if(res != null)
			copyStream(conns.get(0).getInputStream(),res.getOutputStream());
	
	}
	
	private List<HttpURLConnection> createHttpConnections(Iterable<String> urls) throws IOException{
		List<HttpURLConnection> ans = new ArrayList<HttpURLConnection>();
		for(String url: urls){
			if(url != null && url.length() > 0) {
				ans.add(createHttpConnection(url));
			}
		}
		return ans;
	}
	
	private void copyStream(InputStream srcStream,OutputStream dstStream) throws IOException{
		byte[] buf = new byte[100];
		int i = -1;
		while((i = srcStream.read(buf)) != -1){
			dstStream.write(buf, 0, i);
		}
	}
	
	private List<OutputStream> getOutputStreams(List<HttpURLConnection> conns) throws IOException{
		List<OutputStream> ans = new ArrayList<OutputStream>();
		for(HttpURLConnection conn:conns)
			ans.add(conn.getOutputStream());
		return ans;
	}
	private void copyStream(InputStream srcStream,List<OutputStream> dstStreams) throws IOException{
		byte[] buf = new byte[100];
		int i = -1;
		while((i = srcStream.read(buf)) != -1){
			for(OutputStream dstStream:dstStreams)
				dstStream.write(buf, 0, i);
		}
	}
	public String getWebContentByGet(
			String urlString, 
			final String charset,
			int timeout) throws IOException {
		if (urlString == null || urlString.length() == 0) {
			return null;
		}
		
		HttpURLConnection conn = createHttpConnection(urlString);
		
		conn.setRequestMethod("GET");
		// ���ӱ�ͷ��ģ�����������ֹ����
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
		// ֻ����text/html���ͣ���ȻҲ���Խ���ͼƬ,pdf,*/*���⣬����tomcat/conf/web���涨����Щ
		conn.setRequestProperty("Accept", "*/*");
		conn.setConnectTimeout(timeout);
		
		this.modifier.handle(conn);
		
		try {
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,
				charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		if (conn != null) {
			conn.disconnect();
		}
		return sb.toString();

	}

	public String getWebContentByGet(String urlString) throws IOException {
		return getWebContentByGet(urlString, "iso-8859-1", 5000);
	}

	
	public String getWebContentByPost(String urlString, String data,
			final String charset, int timeout) throws IOException {
		if (urlString == null || urlString.length() == 0) {
			return null;
		}
		HttpURLConnection connection = createHttpConnection(urlString);
		
		// �����Ƿ���connection�������Ϊ�����post���󣬲���Ҫ���� http�����ڣ������Ҫ��Ϊtrue
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		// Post ������ʹ�û���
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		// connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		// ���ӱ�ͷ��ģ�����������ֹ����
		connection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows vista)");
		// ֻ����text/html���ͣ���ȻҲ���Խ���ͼƬ,pdf,*/*����
		connection.setRequestProperty("Accept", "*/*");// text/html
		connection.setConnectTimeout(timeout);
		
		
		this.modifier.handle(connection);
		connection.connect();
		
		
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());
		// String content = data;//+URLEncoder.encode("���� ", "utf-8");
		// out.writeBytes(content);
		byte[] content = data.getBytes("UTF-8");// +URLEncoder.encode("���� ",
												// "utf-8");
		out.write(content);
		out.flush();
		out.close();
		try {
			// ����д�ڷ������ݵĺ���
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		String result = StreamHelper.readStream(connection.getInputStream(),charset);
		
		if (connection != null) {
			connection.disconnect();
		}
		
		return result;
	}

	public String getWebContentByPost(String urlString, String data)
			throws IOException {
		return getWebContentByPost(urlString, data, "UTF-8", 5000);// iso-8859-1
	}
	
	
	private HttpURLConnection createHttpConnection(String urlString) throws IOException{
		urlString = (urlString.startsWith("http://") || urlString
				.startsWith("https://")) ? urlString : ("http://" + urlString)
				.intern();
		URL url = new URL(urlString);
		return (HttpURLConnection)url.openConnection();
	}
	
}
