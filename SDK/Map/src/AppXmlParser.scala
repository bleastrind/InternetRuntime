import scala.collection.mutable.Map;
class AppXmlParser {
  
    private val xmlFile = 
	    <RoutingInstance>
    	<id>routingInstance</id>
    	<signal id="1" runat="client">
         <from>weiboAppID</from>
         <name>share</name>
         <vars>
         	<var><key>message</key></var>     
         </vars>
        </signal>
        <adapter from="1" to="2">
         <mapper>
         <key from="message" to="message"/>
         <value transformer="default"/>
         </mapper>
        </adapter>
        <signalListener id="2" type="httpget" runat="renrenAppID">
         <url>http://127.0.0.1/renrent/transfer/message</url>
         <params>
         	<param><key>message</key><value><var/></value></param>
         </params>
        </signalListener>
	</RoutingInstance>

    def getFrom(): String = {
      val signal = xmlFile \ "signal";
	  val from = (signal \ "from").text
	  println(from);
	  return from.toString();
    }
    
    def getTo(): String = {
	  val signalListener = xmlFile \ "signalListener"
	  val requestType = signalListener \ "@runat";
	  return requestType.toString();
	}
    
	def getMap() : Map[String, String]= {
	  val map = scala.collection.mutable.Map.empty[String, String];
	  val adapter = xmlFile \ "adapter"
	  val mapper = adapter \ "mapper"
	  val key = mapper \ "key"
	  val fromParam = key \ "@from"
	  val toParam = key \ "@to"
	  map += (fromParam.toString() -> toParam.toString());
	  return map;
	}
	
	def getReqType(): String = {
	  val signalListener = xmlFile \ "signalListener"
	  val requestType = signalListener \ "@type";
	  return requestType.toString();
	}
	
	def getReqUrl(): String = {
	  val signalListener = xmlFile \ "signalListener";
	  val requestUrl = (signalListener \ "url").text;
	  return requestUrl.toString();
	}
}

object AppXmlParser{
  def main(args: Array[String]){
      val map = scala.collection.mutable.Map.empty[String, String];
      map += ("message"->"feifei")
	  val temp = new Adapter();
	  val tempString = temp adapter(map);
  }
}