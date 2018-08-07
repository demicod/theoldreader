package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.model.BaseRequest
import com.github.demicod.theoldreader.model.ClientLoginRequest
import org.junit.Assert
import org.junit.Test

class AuthApiTest : BaseApiTest() {

	private val api = AuthApi(config)
	private val email = "somebody@somewhere.net"
	private val password = "password"

	@Test
	fun testLogin() {
		val result = api.login(ClientLoginRequest(email, password))
		Assert.assertNotNull(result)
		Assert.assertNotNull(result!!.Auth)
		logger.debug { result }
	}

	@Test
	fun testToken() {
		val result = api.token(BaseRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}
}
