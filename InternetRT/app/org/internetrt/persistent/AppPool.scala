package org.internetrt.persistent

trait AppPool {
	def getAppSecretByID(id:String):String
}

class StubAppPool extends AppPool{
  def getAppSecretByID(id:String)={
    "secret"
  }
}