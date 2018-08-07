package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.model.BaseRequest
import org.junit.Assert
import org.junit.Test

class UserApiTest : BaseApiTest() {

	private val api = UserApi(config)

	@Test
	fun testUserInfo() {
		val result = api.info(BaseRequest(token))
		Assert.assertNotNull(result)
		logger.info { result }
	}
}
