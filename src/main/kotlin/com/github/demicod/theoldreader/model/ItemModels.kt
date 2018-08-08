package com.github.demicod.theoldreader.model

import java.time.Instant

data class ItemId(
		val id: String,
		val directStreamIds: List<String>,
		val timestampUsec: Long
)

data class ItemIds(
		val itemRefs: List<ItemId>,
		val continuation: Int
)

data class ItemList(
		val direction: String,
		val id: String,
		val title: String,
		val description: String? = "",
		val self: Reference,
		val alternate: Reference?,
		val updated: Long,
		val items: List<ItemContent>?
)

data class ItemContent(
		val crawlTimeMsec: Long,
		val timestampUsec: Long,
		val id: String,
		val categories: List<String>,
		val title: String,
		val update: Long,
		val tor_identifier: String,
		val canonical: List<Reference>,
		val alternate: List<Reference>,
		val summary: Summary,
		val author: String,
		val annotations: List<Annotation>,
		val likingUsers: List<LikingUser>,
		val likingUsersCount: Int,
		val comments: List<Comment>,
		val origin: ItemOrigin
)

data class ItemOrigin(
		val streamId: String?,
		val title: String?,
		val htmlUrl: String?,
		val feed_title: String?
)

data class Summary(
		val direction: String,
		val content: String
)

data class Reference(
		val href: String,
		val type: String?
)

data class LikingUser(
		val userIds: List<String>,
		val stream: String,
		val displayName: String
)

data class Annotation(
		val text: String,
		val origin: Origin
)

data class Origin(
		val userIds: List<String>,
		val stream: String,
		val displayName: String,
		val iconUrl: String,
		val htmlUrl: String
)

data class Comment(
		val id: String,
		val comment: String,
		val timestamp: Long,
		val origin: Origin
)

open class ItemRequest @JvmOverloads constructor(
		override val token: String,
		open val limit: Int? = null,
		open val continuation: Int? = null,
		open val unread: Boolean? = false,
		open val reverseOrder: Boolean? = false,
		open val olderThan: Instant? = null,
		open val newerThan: Instant? = null
) : BaseRequest(token)

data class SubscriptionItemRequest @JvmOverloads constructor(
		override val token: String,
		val subscriptionId: String,
		override val limit: Int? = null,
		override val continuation: Int? = null,
		override val unread: Boolean? = false,
		override val reverseOrder: Boolean? = false,
		override val olderThan: Instant? = null,
		override val newerThan: Instant? = null
) : ItemRequest(token, limit, continuation, unread, reverseOrder, olderThan, newerThan)

data class FriendItemRequest @JvmOverloads constructor(
		override val token: String,
		val friendId: String,
		override val limit: Int? = null,
		override val continuation: Int? = null,
		override val unread: Boolean? = false,
		override val reverseOrder: Boolean? = false,
		override val olderThan: Instant? = null,
		override val newerThan: Instant? = null
) : ItemRequest(token, limit, continuation, unread, reverseOrder, olderThan, newerThan)

data class FolderItemRequest @JvmOverloads constructor(
		override val token: String,
		val folder: String,
		override val limit: Int? = null,
		override val continuation: Int? = null,
		override val unread: Boolean? = false,
		override val reverseOrder: Boolean? = false,
		override val olderThan: Instant? = null,
		override val newerThan: Instant? = null
) : ItemRequest(token, limit, continuation, unread, reverseOrder, olderThan, newerThan)

open class MarkAsReadRequest @JvmOverloads constructor(
		override val token: String,
		open val olderThan: Instant? = null
) : BaseRequest(token)

data class FriendMarkAsReadRequest @JvmOverloads constructor(
		override val token: String,
		val friendId: String,
		override val olderThan: Instant? = null
) : MarkAsReadRequest(token, olderThan)

data class FolderMarkAsReadRequest @JvmOverloads constructor(
		override val token: String,
		val folder: String,
		override val olderThan: Instant? = null
) : MarkAsReadRequest(token, olderThan)

data class SubscriptionMarkAsReadRequest @JvmOverloads constructor(
		override val token: String,
		val subscriptionId: String,
		override val olderThan: Instant? = null
) : MarkAsReadRequest(token, olderThan)

data class ItemContentRequest(
		override val token: String,
		val itemIds: List<String>
) : BaseRequest(token)

data class CommentRequest(
		override val token: String,
		val itemId: String,
		val comment: String
): BaseRequest(token)
