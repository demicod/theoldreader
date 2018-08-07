package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.Config
import com.github.demicod.theoldreader.model.UserInfo
import com.github.demicod.theoldreader.model.BaseRequest

const val USER_INFO_URI = "$BASE_API_URI/user-info"

class UserApi(config: Config) : BaseApi(config) {

	fun info(request: BaseRequest): UserInfo? {
		return doGet(request.token, USER_INFO_URI)
	}
}