package org.internetrt.core.security

trait AccessControlSystem {
	def checkAccess(userID:String ,appID:String ,action:String):Boolean
}