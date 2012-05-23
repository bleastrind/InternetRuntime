package org.internetrt.test
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.internetrt.core.signalsystem._
import org.specs2.mock.Mockito
import org.internetrt.core.configuration._
import org.internetrt.core.InternetRuntime
import org.internetrt.core.io.IOManagerComponent
import org.internetrt.core.Components
import org.internetrt.core.security.SecurityPrivacyComponent
import org.internetrt.core.io.IOManager
import org.internetrt.core.security.AuthCenter


class SignalRoutingSpec extends Specification with Mockito{
	"Get redirect response in logical model" in {
	  running(FakeApplication()){  
//	    "Hello" must be startWith("Hello")
//	    new InternetRuntime 
//	    	with SignalSystemComponent
//	    	{
//	      val signalSystem = new MockSignalSystem
//	      
//	      class MockSignalSystem extends ISignalSystem{
//	        def handleSignal(t:Signal):SignalResponse= null
//	      }
//	    }.signalSystem.handleSignal(null) === null;
	  }
	  TestEnvironment.signalSystem.handleSignal(null) must beEqualTo(null)
	}
	
	object TestEnvironment extends InternetRuntime{
	  val signalSystem = mock[SignalSystem]
	  val configurationSystem = mock[ConfigurationSystem]
	  val ioManager = mock[IOManager]
	  val authCenter = mock[AuthCenter]
	}
	object TestInternetRuntime extends InternetRuntime{
	    val components = TestEnvironment
	}
//	class MockitoSpec extends Specification { def is =
//
//    "A java list can be mocked"                                                    ^
//      "You can make it return a stubbed value"                                     ! c().stub^
//      "You can verify that a method was called"                                    ! c().verify^
//      "You can verify that a method was not called"                                ! c().verify2^
//                                                                                   end
//    case class c() extends Mockito {
//      val m = org.specs2.mock.MockitoMocker. // a concrete class would be mocked with: mock[new java.util.LinkedList[String]]
//      def stub = {
//        m.get(0) returns "one"             // stub a method call with a return value
//        m.get(0) must_== "one"             // call the method
//      }
//      def verify = {
//        m.get(0) returns "one"             // stub a method call with a return value
//        m.get(0)                           // call the method
//        there was one(m).get(0)            // verify that the call happened
//      }
//      def verify2 = there was no(m).get(0) // verify that the call never happened
//    }
//  }
}