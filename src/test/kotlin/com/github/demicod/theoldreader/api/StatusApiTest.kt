package com.github.demicod.theoldreader.api

import org.junit.Assert
import org.junit.Test

class StatusApiTest : BaseApiTest() {

	private val api = StatusApi(config)

	@Test
	fun testStatus() {
		val result = api.status()
		Assert.assertNotNull(result)
		logger.debug { result }
	}
}