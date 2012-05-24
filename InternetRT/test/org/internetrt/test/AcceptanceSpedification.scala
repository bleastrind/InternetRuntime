package org.internetrt.test
import org.specs2.mutable.Specification
import org.specs2._
import org.specs2.specification._

class SignalSpedification extends Specification{ override def is =
    "This is a specification to check the signal dispatching works well"         ^
                                                                                p^
    "The normal signal should"                                                   ^
      "[Function]notify the intereptors"                                     ! e1^
      "[Safe]start with 'Hello'"                                             ! e2^
      "[Performance]end with 'world'"                                        ! e3^
                                                                                p^
    "The sip type signal should"  ^
      "Given the sip request" ^ Step(request) ^
      "Receive a sip response" ^ Step(sip) ^
      "Arise a request to client" ^ Step(requesttoclient) ^
      "Received the result" ^ Step(data) ^
      end
    
    def e1 = "Hello world" must have size(11)
    def e2 = "Hello world" must startWith("Hello")
    def e3 = "Hello world" must endWith("world")

	object request extends Given[Any]{
		def extract(text: String):Any = 1
	}	
	object sip extends When[Any,Any]{
		def extract(p:Any, text:String) = 2
	}
	object requesttoclient extends When[Any,Any]{
	  def extract(p:Any, text:String) = 2
	}
	object data extends Then[Any]{
	  def extract(p:Any, text:String) = 2 === 2
	}
  }