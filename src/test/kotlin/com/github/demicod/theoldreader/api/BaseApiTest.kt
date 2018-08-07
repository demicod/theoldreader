package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.Config
import com.github.kittinunf.fuel.Fuel
import mu.KLogging

abstract class BaseApiTest : KLogging() {

	protected val config = Config("https://theoldreader.com", "kotlin")
	protected val token = "token"

	init {
		Fuel.testMode {
			timeout = 15000
		}
	}
}
