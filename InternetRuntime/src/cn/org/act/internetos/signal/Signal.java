package cn.org.act.internetos.signal;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.org.act.tools.StreamHelper;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;


public abstract class Signal {
	
	@XStreamOmitField
	private int id;
	
	private String url;
	
	private String method;

	@XStreamConverter(HeadersConvertor.class)
	private JsonMap headers;

	@XStreamConverter(StreamConvertor.class)
	private InputStream data;

	public void setHeaders(Map<String,String> headers) {
		this.headers = new JsonMap(headers);
	}
	
	public Map<String,String> getHeaders() {
		return headers;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethod() {
		return method;
	}
	public void setData(InputStream data){
		this.data = data;
	}
	public InputStream getData() throws IOException {
		return data;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void makeDataRereadable() {		
	}
	
	public String toString(){
		JsonHierarchicalStreamDriver jsondriver = new JsonHierarchicalStreamDriver(){
			public HierarchicalStreamWriter createWriter(Writer writer) {
		        return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
		    }
		};
		
		XStream xstream = new XStream(jsondriver);
		xstream.autodetectAnnotations(true);
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.aliasSystemAttribute(null, "class"); // Remove xml
		return xstream.toXML(this);
	}
	
	public static class HeadersConvertor implements Converter{

		@Override
		public boolean canConvert(Class arg0) {
			
			return JsonMap.class.equals(arg0);
		}

		@Override
		public void marshal(Object value, HierarchicalStreamWriter writer,
				MarshallingContext context) {
			JsonMap headers = (JsonMap) value;
			writer.startNode("data");
			for(Entry<String,String> entry: headers.entrySet()){
				
				writer.startNode(entry.getKey());
				writer.setValue(entry.getValue());
				writer.endNode();
			}
			writer.endNode();
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader arg0,
				UnmarshallingContext arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	private static class JsonMap extends HashMap<String,String>{
		public JsonMap(Map<String,String> map)
		{
			super(map);
		}
	}
	public static class StreamConvertor implements Converter {

		@Override
		public boolean canConvert(Class clazz) {
			return InputStream.class.isAssignableFrom(clazz);
		}

		@Override
		public void marshal(Object value, HierarchicalStreamWriter writer,
				MarshallingContext context) {
			InputStream stream = (InputStream) value;
			writer.setValue(StreamHelper.readStream(stream));
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
				UnmarshallingContext context) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}	
	
	public abstract void sendTo(List<SignalListener> listener,OutputStream result) throws IOException;
	
	public static void main(String[] args){
		Signal s = new AsyncSignal("callback", "usertoken");
		s.setUrl("url");
		try {
			s.setData(new ByteArrayInputStream("he\nllo".getBytes("UTF-8")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		s.setMethod("get");
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("typed", "cnointernetosalert");
		headers.put("async", "true1");
		s.setHeaders(headers);
		
		System.out.println(s.toString());
	}

}



