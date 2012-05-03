package org.internetrt.core.signalsystem

/**
 * A signalResponse provide the data need to response a signal
 */
trait SignalResponse {
  def getResponse:String
}

class ObjectResponse(o:Any) extends SignalResponse{
  def getResponse = o.toString()
}
/**
 * A SignalHeadResponse provide the brief info about how to handle the signal
 */
//trait SignalHeadResponse extends SignalResponse{
//  def getHead:String
//}

/**
 * Contain the signalListeners who received the signal
 */
class EventSignalResponse{
  
}

/**
 * Contain the 
 */
class RequestSignalResponse{
  
}