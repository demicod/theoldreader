package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.Config
import com.github.demicod.theoldreader.model.Status

const val STATUS_URI = "$BASE_API_URI/status"

class StatusApi(config: Config) : BaseApi(config) {

	fun status(): Status? {
		return doGet(STATUS_URI)
	}
}
