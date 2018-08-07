package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.Config
import com.github.demicod.theoldreader.model.AddSubscription
import com.github.demicod.theoldreader.model.AddSubscriptionRequest
import com.github.demicod.theoldreader.model.BaseRequest
import com.github.demicod.theoldreader.model.ExportSubscriptionRequest
import com.github.demicod.theoldreader.model.RemoveSubscriptionRequest
import com.github.demicod.theoldreader.model.SubscriptionList
import com.github.demicod.theoldreader.model.UnreadCountList
import com.github.demicod.theoldreader.model.UpdateSubscriptionRequest

const val UNREAD_COUNT_URI = "$BASE_API_URI/unread-count"
const val SUBSCRIPTION_LIST_URI = "$BASE_API_URI/subscription/list"
const val EDIT_SUBSCRIPTION_URI = "$BASE_API_URI/subscription/edit"
const val EXPORT_SUBSCRIPTION_URI = "/reader/subscriptions/export"
const val ADD_SUBSCRIPTION_URI = "$BASE_API_URI/subscription/quickadd"

class SubscriptionApi(config: Config) : BaseApi(config) {

	fun unreadCount(request: BaseRequest): UnreadCountList? {
		return doGet(request.token, UNREAD_COUNT_URI)
	}

	fun subscriptionList(request: BaseRequest): SubscriptionList? {
		return doGet(request.token, SUBSCRIPTION_LIST_URI)
	}

	fun exportOPML(request: ExportSubscriptionRequest): String? {
		return doDownload(request.token, EXPORT_SUBSCRIPTION_URI, request.outputDir)
	}

	fun addSubscription(request: AddSubscriptionRequest): AddSubscription? {
		val parameters = listOf(
				"quickadd" to request.url
		)
		return doPost(request.token, ADD_SUBSCRIPTION_URI, parameters)
	}

	fun updateSubscription(request: UpdateSubscriptionRequest): String? {
		val parameters = mutableListOf(
				"ac" to "edit",
				"s" to request.streamId
		)
		if (!request.title.isNullOrBlank()) {
			parameters.add("t" to request.title!!)
		}
		if (!request.folder.isNullOrBlank()) {
			if (request.folder.equals("default")) {
				parameters.add("r" to request.folder!!)
			} else {
				parameters.add("a" to request.folder!!)
			}
		}
		return doPost(request.token, EDIT_SUBSCRIPTION_URI, parameters)
	}

	fun removeSubscription(request: RemoveSubscriptionRequest): String? {
		val parameters = listOf(
				"ac" to "unsubscribe",
				"s" to request.streamId
		)
		return doPost(request.token, EDIT_SUBSCRIPTION_URI, parameters)
	}
}
