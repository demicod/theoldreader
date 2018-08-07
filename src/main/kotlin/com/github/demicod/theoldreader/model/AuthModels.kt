package com.github.demicod.theoldreader.model

enum class AccountType {
	HOSTED_OR_GOOGLE, HOSTED, GOOGLE
}

data class ClientLogin(
		val SID: String,
		val LSID: String,
		val Auth: String)

data class ClientLoginRequest (
		val email: String,
		val passwd: String,
		val accountType: AccountType? = AccountType.HOSTED_OR_GOOGLE,
		val service: String? = "reader")

data class Token(val token: String)
