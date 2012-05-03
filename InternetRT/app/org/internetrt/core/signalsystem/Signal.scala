package org.internetrt.core.signalsystem

trait Signal {
	def getIdentifier():String //Identifier should be able to recognize the signal and its source  
}

/**
 * A Event signal is a signal don't need response
 */
class EventSignal{
  
}

/**
 * A Request signal is a signal need sync response
 */
class RequestSignal{
  
}