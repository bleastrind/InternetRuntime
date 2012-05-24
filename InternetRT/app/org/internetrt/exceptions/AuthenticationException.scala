package org.internetrt.exceptions

case class AuthenticationException(msg:String) extends Exception {

}

class AuthDelayException(msg:String) extends AuthenticationException(msg){
    def this() = this("")
}