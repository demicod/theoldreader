package com.github.demicod.theoldreader.model

open class BaseRequest(
		open val token: String
)

data class ErrorList(
		val errors: List<String>
)
