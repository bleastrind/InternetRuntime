package cn.org.act.demo.pickapicture.server;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ManageMessage {

	static private HashMap<String,BlockingQueue<String>> UserMessageMap = new HashMap<String,BlockingQueue<String>>();
	
	public static BlockingQueue<String> prepareMessageQueue(String userToken)
	{
		BlockingQueue<String> messageQueue =  new LinkedBlockingQueue<String>();
		UserMessageMap.put(userToken, messageQueue);
		return messageQueue;
	}
	
	public static void receiveMessage (String userToken,String message)
	{
		BlockingQueue<String> queue = UserMessageMap.get(userToken);
		try {
			queue.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("抛出异常，客户端没有拿到所选择的图片");
			e.printStackTrace();
		}
	}
	
}
