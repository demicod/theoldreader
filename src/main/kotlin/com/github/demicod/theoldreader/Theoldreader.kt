package com.github.demicod.theoldreader

import com.github.demicod.theoldreader.api.AuthApi
import com.github.demicod.theoldreader.api.FolderApi
import com.github.demicod.theoldreader.api.FriendApi
import com.github.demicod.theoldreader.api.ItemApi
import com.github.demicod.theoldreader.api.StatusApi
import com.github.demicod.theoldreader.api.SubscriptionApi
import com.github.demicod.theoldreader.api.UserApi

class Theoldreader(val config: Config) {
	val auth = AuthApi(config)
	val folders = FolderApi(config)
	val friends = FriendApi(config)
	val items = ItemApi(config)
	val subscriptions = SubscriptionApi(config)
	val users = UserApi(config)

	private val status = StatusApi(config)

	fun status() = status.status()
}