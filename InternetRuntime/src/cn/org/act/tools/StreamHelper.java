package cn.org.act.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamHelper {
	public static void copyStream(InputStream srcStream, OutputStream dstStream) throws IOException {
		byte[] buf = new byte[100];
		int i = -1;
		try {
			while ((i = srcStream.read(buf)) != -1) {
				dstStream.write(buf, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("The stream is not avaliable!");
		}
	}
	public static String readStream(InputStream stream,String charset) throws IOException
	{
		if(stream == null)
			return null;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				stream, charset));
		String line = "";
		line +=reader.readLine();
		StringBuffer sb = new StringBuffer(line);
		while ((line = reader.readLine()) != null) {
			sb.append("\n").append(line);
		}
		if (reader != null) {
			reader.close();
		}
		return sb.toString();
	}
	
	public static String readStream(InputStream stream){
		try {
			return readStream(stream,"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
