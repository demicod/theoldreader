package com.github.demicod.theoldreader.exception

class ApiException : Exception {

	constructor(message: String?) : super(message)

	constructor(message: String?, cause: Throwable?) : super(message, cause)
}
