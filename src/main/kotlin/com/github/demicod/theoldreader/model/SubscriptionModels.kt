package com.github.demicod.theoldreader.model

data class AddSubscription(
		val query: String,
		val numResults: String,
		val streamId: String?,
		val error: String?)

data class AddSubscriptionRequest(
		override val token: String,
		val url: String) : BaseRequest(token)

data class ExportSubscriptionRequest(
		override val token: String,
		val outputDir: String? = null) : BaseRequest(token)

data class RemoveSubscriptionRequest(
		override val token: String,
		val streamId: String) : BaseRequest(token)

data class Subscription(
		val id: String,
		val title: String?,
		val categories: List<Category>,
		val sortid: String,
		val firstitemmsec: Long,
		val url: String,
		val htmlUrl: String?,
		val iconUrl: String?)

data class SubscriptionList(
		val subscriptions: List<Subscription>)

data class UnreadCount(
		val id: String,
		val count: Int,
		val newestItemTimestampUsec: Long)

data class UnreadCountList(
		val max: Int,
		val unreadcounts: List<UnreadCount>)

data class UpdateSubscriptionRequest @JvmOverloads constructor(
		override val token: String,
		val streamId: String,
		val title: String? = "",
		val folder: String? = "") : BaseRequest(token)

data class Category(
		val id: String,
		val label: String
)
