class Adapter {
	def adapter(sourceMap : scala.collection.mutable.Map[String,String]): scala.collection.mutable.Map[String,String] = {
	    val targetMap = scala.collection.mutable.Map.empty[String, String]
	    val appParser = new AppXmlParser();
	    val transMap = appParser.getMap();
	    for(elemI<-sourceMap){
	      for(elemJ<-transMap){
	        if(elemI._1==elemJ._1){
	          targetMap += (elemJ._2->elemI._2)
	        }
	      }
	    }
	    return targetMap
	}
}