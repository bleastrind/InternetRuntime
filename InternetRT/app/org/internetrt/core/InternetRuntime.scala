package org.internetrt.core

import signalsystem.SignalSystemComponent
import configuration.ConfigurationSystemComponent
import io.IOManagerComponent

object InternetRuntime{
    this:SignalSystemComponent with ConfigurationSystemComponent with IOManagerComponent=>
	
      def getProcessingInfo():String=""
  
}
