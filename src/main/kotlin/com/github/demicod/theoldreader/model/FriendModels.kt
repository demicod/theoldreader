package com.github.demicod.theoldreader.model

data class Friend(
		val userIds: List<String>,
		val stream: String,
		val displayName: String,
		val iconUrl: String
)

data class FriendList(
		val friends: List<Friend>
)

data class FriendRequest(
		override val token: String,
		val id: String
) : BaseRequest(token)
