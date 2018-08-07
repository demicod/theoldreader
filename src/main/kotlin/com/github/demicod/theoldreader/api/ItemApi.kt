package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.Config
import com.github.demicod.theoldreader.model.CommentRequest
import com.github.demicod.theoldreader.model.FolderItemRequest
import com.github.demicod.theoldreader.model.FolderMarkAsReadRequest
import com.github.demicod.theoldreader.model.FriendItemRequest
import com.github.demicod.theoldreader.model.FriendMarkAsReadRequest
import com.github.demicod.theoldreader.model.ItemContentRequest
import com.github.demicod.theoldreader.model.ItemIds
import com.github.demicod.theoldreader.model.ItemList
import com.github.demicod.theoldreader.model.ItemRequest
import com.github.demicod.theoldreader.model.MarkAsReadRequest
import com.github.demicod.theoldreader.model.SubscriptionItemRequest
import com.github.demicod.theoldreader.model.SubscriptionMarkAsReadRequest

const val STREAM_CONTENTS_URI = "$BASE_API_URI/stream/contents"
const val ITEM_CONTENTS_URI = "$BASE_API_URI/stream/items/contents"
const val ITEM_IDS_URI = "$BASE_API_URI/stream/items/ids"
const val MARK_ALL_AS_READ_URI = "$BASE_API_URI/mark-all-as-read"
const val EDIT_TAG_URL = "$BASE_API_URI/edit-tag"
const val EDIT_COMMENT_URI = "$BASE_API_URI/comment/edit"

const val ALL_ITEMS_FILTER = "user/-/state/com.google/reading-list"
const val READ_ITEMS_FILTER = "user/%s/state/com.google/read"
const val STARRED_ITEMS_FILTER = "user/-/state/com.google/starred"
const val LIKED_ITEMS_FILTER = "user/-/state/com.google/like"
const val SHARED_ITEMS_FILTER = "user/%s/state/com.google/broadcast"
const val COMMENTED_ITEMS_FILTER = "user/-/state/com.google/broadcast-friends-comments"
const val SHARED_BY_FRIENDS_ITEMS_FILTER = "user/-/state/com.google/broadcast-friends"
const val FOLDER_FILTER = "user/-/label/%s"

const val DEFAULT_ID = "-"

const val ITEM_ID = "tag:google.com,2005:reader/item/%s"

class ItemApi(config: Config) : BaseApi(config) {

	fun addComment(request: CommentRequest): String? {
		val parameters = listOf(
				"action" to "addcomment",
				"i" to ITEM_ID.format(request.itemId),
				"comment" to request.comment
		)
		return doPost(request.token, EDIT_COMMENT_URI, parameters)
	}

	fun itemContents(request: ItemContentRequest): ItemList? {
		val parameters = request.itemIds.map { itemId ->
			"i" to ITEM_ID.format(itemId)
		}
		return doPost(request.token, ITEM_CONTENTS_URI, parameters)
	}

	fun allItemIds(request: ItemRequest) =
			itemIds(request, ALL_ITEMS_FILTER)

	fun allItemContents(request: ItemRequest) =
			streamContents(request, ALL_ITEMS_FILTER)

	fun readItemIds(request: ItemRequest) =
			itemIds(request, READ_ITEMS_FILTER)

	fun readItemContents(request: ItemRequest) =
			streamContents(request, READ_ITEMS_FILTER)

	fun starredItemIds(request: ItemRequest) =
			itemIds(request, STARRED_ITEMS_FILTER)

	fun starredItemContents(request: ItemRequest) =
			streamContents(request, STARRED_ITEMS_FILTER)

	fun likedItemIds(request: ItemRequest) =
			itemIds(request, LIKED_ITEMS_FILTER)

	fun likedItemContents(request: ItemRequest) =
			streamContents(request, LIKED_ITEMS_FILTER)

	fun sharedItemIds(request: ItemRequest) =
			itemIds(request, SHARED_ITEMS_FILTER.format(DEFAULT_ID))

	fun sharedItemContents(request: ItemRequest) =
			streamContents(request, SHARED_ITEMS_FILTER.format(DEFAULT_ID))

	fun commentedItemIds(request: ItemRequest) =
			itemIds(request, COMMENTED_ITEMS_FILTER)

	fun commentedItemContents(request: ItemRequest) =
			streamContents(request, COMMENTED_ITEMS_FILTER)

	fun sharedByFriendsItemIds(request: ItemRequest) =
			itemIds(request, SHARED_BY_FRIENDS_ITEMS_FILTER)

	fun sharedByFriendsItemContents(request: ItemRequest) =
			streamContents(request, SHARED_BY_FRIENDS_ITEMS_FILTER)

	fun subscriptionItems(request: SubscriptionItemRequest) =
			itemIds(request, request.subscriptionId)

	fun subscriptionItemContents(request: SubscriptionItemRequest) =
			streamContents(request, request.subscriptionId)

	fun friendItems(request: FriendItemRequest) =
			itemIds(request, SHARED_ITEMS_FILTER.format(request.friendId))

	fun folderItems(request: FolderItemRequest) =
			itemIds(request, FOLDER_FILTER.format(request.folder))

	fun markAllAsRead(request: MarkAsReadRequest) =
			markItemsAsRead(request, ALL_ITEMS_FILTER)

	fun markSharedByFriendsAsRead(request: MarkAsReadRequest) =
			markItemsAsRead(request, SHARED_BY_FRIENDS_ITEMS_FILTER)

	fun markSharedByFriendAsRead(request: FriendMarkAsReadRequest) =
			markItemsAsRead(request, SHARED_ITEMS_FILTER.format(request.friendId))

	fun markFolderItemsAsRead(request: FolderMarkAsReadRequest) =
			markItemsAsRead(request, FOLDER_FILTER.format(request.folder))

	fun markSubscriptionItemsAsRead(request: SubscriptionMarkAsReadRequest) =
			markItemsAsRead(request, request.subscriptionId)

	fun markAsStarred(request: ItemContentRequest) =
			addMark(request, STARRED_ITEMS_FILTER)

	fun markAsLiked(request: ItemContentRequest) =
			addMark(request, LIKED_ITEMS_FILTER)

	fun markAsShared(request: ItemContentRequest, note: String? = null) =
			addMark(request, SHARED_ITEMS_FILTER.format(DEFAULT_ID) + (if (note != null) "&annotation=$note" else ""))

	fun markAsUnread(request: ItemContentRequest) =
			removeMark(request, READ_ITEMS_FILTER.format(DEFAULT_ID))

	fun removeStarredMark(request: ItemContentRequest) =
			removeMark(request, STARRED_ITEMS_FILTER)

	fun removeLikedMark(request: ItemContentRequest) =
			removeMark(request, LIKED_ITEMS_FILTER)

	fun removeSharedMark(request: ItemContentRequest) =
			removeMark(request, SHARED_ITEMS_FILTER.format(DEFAULT_ID))

	private fun markItemsAsRead(request: MarkAsReadRequest, s: String): String? {
		val parameters = mutableListOf("s" to s)
		val olderThan = request.olderThan
		if (olderThan != null) {
			parameters.add("ts" to (olderThan.epochSecond * 1_000_000_000 + olderThan.nano).toString())
		}
		return doPost(request.token, MARK_ALL_AS_READ_URI, parameters)
	}

	private fun itemRequestParameters(request: ItemRequest, s: String): List<Pair<String, String>> {
		val parameters = mutableListOf("s" to s)
		val unread = request.unread
		if (unread != null && unread) {
			parameters.add("xt" to READ_ITEMS_FILTER.format(DEFAULT_ID))
		}
		val limit = request.limit
		if (limit != null && limit > 0) {
			parameters.add("n" to request.limit.toString())
		}
		val reverseOrder = request.reverseOrder
		if (reverseOrder != null && reverseOrder) {
			parameters.add("r" to "o")
		}
		val continuation = request.continuation
		if (continuation != null && continuation > 0) {
			parameters.add("c" to request.continuation.toString())
		}
		val olderThan = request.olderThan
		if (olderThan != null) {
			parameters.add("nt" to olderThan.epochSecond.toString())
		}
		val newerThan = request.newerThan
		if (newerThan != null) {
			parameters.add("ot" to newerThan.epochSecond.toString())
		}
		return parameters
	}

	private fun itemIds(request: ItemRequest, s: String): ItemIds? {
		val parameters = itemRequestParameters(request, s)
		return doGet(request.token, ITEM_IDS_URI, parameters)
	}

	private fun streamContents(request: ItemRequest, s: String): ItemList? {
		val parameters = itemRequestParameters(request, s)
		return doGet(request.token, STREAM_CONTENTS_URI, parameters)
	}

	private fun addMark(request: ItemContentRequest, a: String): String? {
		val parameters = mutableListOf("a" to a)
		parameters.addAll(request.itemIds.map { itemId ->
			"i" to ITEM_ID.format(itemId)
		})
		return doPost(request.token, EDIT_TAG_URL, parameters)
	}

	private fun removeMark(request: ItemContentRequest, r: String): String? {
		val parameters = mutableListOf("r" to r)
		parameters.addAll(request.itemIds.map { itemId ->
			"i" to ITEM_ID.format(itemId)
		})
		return doPost(request.token, EDIT_TAG_URL, parameters)
	}
}
