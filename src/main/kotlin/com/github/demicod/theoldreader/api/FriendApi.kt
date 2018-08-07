package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.Config
import com.github.demicod.theoldreader.model.BaseRequest
import com.github.demicod.theoldreader.model.FriendList
import com.github.demicod.theoldreader.model.FriendRequest

const val FRIEND_LIST_URI = "$BASE_API_URI/friend/list"
const val EDIT_FRIEND_URI = "$BASE_API_URI/friend/edit"

class FriendApi(config: Config) : BaseApi(config) {

	fun friendList(request: BaseRequest): FriendList? {
		return doGet(request.token, FRIEND_LIST_URI)
	}

	fun follow(request: FriendRequest): String? {
		return editFriend("addfollowing", request)
	}

	fun unfollow(request: FriendRequest): String? {
		return editFriend("removefollowing", request)
	}

	private fun editFriend(action: String, request: FriendRequest): String? {
		val parameters = listOf(
				"action" to action,
				"u" to request.id)
		return doPost(request.token, EDIT_FRIEND_URI, parameters)
	}
}
