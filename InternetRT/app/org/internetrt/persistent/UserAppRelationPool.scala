package org.internetrt.persistent

trait UserAppRelationPool {
	def getAppIDsByUserID(userID:String):Seq[String]
}