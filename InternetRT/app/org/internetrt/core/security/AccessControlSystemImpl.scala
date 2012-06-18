package org.internetrt.core.security

import org.internetrt.persistent.ApplicationAccessPool

abstract class AccessControlSystemImpl extends AccessControlSystem {
    val applicationAccessPool:ApplicationAccessPool
	def checkAccess(userID:String ,appID:String ,action:String):Boolean = {
	  applicationAccessPool.get(userID+appID).getOrElse(Seq.empty).contains(action)
	}
	
	def confirmAccess(userID:String, appID:String, actions:Seq[String]) = {
	  applicationAccessPool.put(userID+appID,actions)
	}
}