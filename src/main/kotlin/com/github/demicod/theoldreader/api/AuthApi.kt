package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.Config
import com.github.demicod.theoldreader.model.BaseRequest
import com.github.demicod.theoldreader.model.ClientLogin
import com.github.demicod.theoldreader.model.ClientLoginRequest
import com.github.demicod.theoldreader.model.Token

const val LOGIN_URI = "/accounts/ClientLogin"
const val TOKEN_URI = "$BASE_API_URI/token"

class AuthApi(config: Config) : BaseApi(config) {

	fun login(request: ClientLoginRequest): ClientLogin? {
		val parameters = listOf(
				"client" to config.client,
				"accountType" to request.accountType!!.name,
				"service" to request.service!!,
				"Email" to request.email,
				"Passwd" to request.passwd,
				"output" to "json")
		return doPost(LOGIN_URI, parameters)
	}

	fun token(request: BaseRequest): Token? {
		return doGet(request.token, TOKEN_URI)
	}
}
