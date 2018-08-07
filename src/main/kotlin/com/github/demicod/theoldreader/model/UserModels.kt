package com.github.demicod.theoldreader.model

data class UserInfo(
		val userId: String,
		val userName: String,
		val userProfileId: String,
		val userEmail: String,
		val isBloggerUser: Boolean,
		val signupTimeSec: Long,
		val isMultiLoginEnabled: Boolean,
		val isPremium: Boolean)
