package org.internetrt.core.model
import org.internetrt.core.signalsystem.Signal

abstract class Application {
	def requests:Seq[Any]
	def responses:Seq[Any]
}

class Request{
  def matchSignal(s:Signal)=true
}

class Response{
  
}