package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.model.CommentRequest
import com.github.demicod.theoldreader.model.FolderItemRequest
import com.github.demicod.theoldreader.model.FolderMarkAsReadRequest
import com.github.demicod.theoldreader.model.FriendItemRequest
import com.github.demicod.theoldreader.model.FriendMarkAsReadRequest
import com.github.demicod.theoldreader.model.ItemContentRequest
import com.github.demicod.theoldreader.model.ItemRequest
import com.github.demicod.theoldreader.model.MarkAsReadRequest
import com.github.demicod.theoldreader.model.SubscriptionItemRequest
import com.github.demicod.theoldreader.model.SubscriptionMarkAsReadRequest
import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.util.stream.Collectors

class ItemApiTest : BaseApiTest() {

	private val api = ItemApi(config)

	private val _subscriptionId = "feed/5af8931efea0e708c3000289"
	private val _friendId = "41f599dfeefd4f3ff44a3199"
	private val _folder = "The Old Reader Picks"
	private val _markOlderThan = Instant.now().minusSeconds(60 * 60 * 24 * 100)
	private val _markAsStarred = listOf("5b61beac5f45b7a937007233", "5b61bdf32d215d5a26004095")
	private val _markAsLiked = listOf("5b61beac5f45b7a937007233", "5b61bdf32d215d5a26004095")
	private val _markAsShared = listOf("5b61beac5f45b7a937007233", "5b61bdf32d215d5a26004095")
	private val _markAsUnread = listOf("5b61beac5f45b7a937007233", "5b61bdf32d215d5a26004095")
	private val _itemContents = listOf("5b046ba1ca9f40bc9b00d598")

	private val _olderThan = Instant.parse("2018-01-01T00:00:00.Z")
	private val _newerThan = Instant.parse("2018-01-01T00:00:00.Z")

	private val _comment = "Hello there!"
	private val _commentItemId = "5b046ba1ca9f40bc9b00d598"

	@Test
	fun testAddComment() {
		val result = api.addComment(CommentRequest(token, _commentItemId, _comment))
		Assert.assertNotNull(result)
		logger.debug { "Add comment id: $result" }
	}

	@Test
	fun testAllItemIds() {
		val result = api.allItemIds(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testAllItemContents() {
		val idList = api.allItemIds(ItemRequest(token))
		Assert.assertNotNull(idList)
		logger.debug { idList }
		val result = api.allItemContents(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
		val idSet = idList!!.itemRefs.toSet().map { item -> ITEM_ID.format(item.id) }
		logger.debug { "idSet: $idSet" }
		Assert.assertTrue(result!!.items!!.stream().allMatch { item -> idSet.contains(item.id) })
	}

	@Test
	fun testAllItemIdsWithContinuation() {
		val result1 = api.allItemIds(ItemRequest(token))
		Assert.assertNotNull(result1)
		logger.debug { result1 }
		val result2 = api.allItemIds(ItemRequest(token = token, continuation = result1?.continuation))
		Assert.assertNotNull(result2)
		logger.debug { result2 }
	}

	@Test
	fun testAllItemIdsReverseOrder() {
		val result = api.allItemIds(ItemRequest(token = token, reverseOrder = true))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testAllItemIdsWithLimit() {
		val limit = 2
		val result = api.allItemIds(ItemRequest(token = token, limit = limit))
		Assert.assertNotNull(result)
		logger.debug { result }
		Assert.assertEquals(limit, result!!.itemRefs.size)
	}

	@Test
	fun testAllItemIdsUnread() {
		val result = api.allItemIds(ItemRequest(token = token, unread = true))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testAllItemIdsOlderThan() {
		val result = api.allItemIds(ItemRequest(token = token, olderThan = _olderThan))
		Assert.assertNotNull(result)
		logger.debug { result }
		val dateNano = _olderThan.toEpochMilli() * 1000
		result!!.itemRefs.forEach {
			Assert.assertTrue(it.timestampUsec < dateNano)
		}
	}

	@Test
	fun testAllItemIdsWithMultipleConditions() {
		val result = api.allItemIds(ItemRequest(token = token, limit = 5, reverseOrder = true, unread = true))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testAllItemIdsNewerThan() {
		val result = api.allItemIds(ItemRequest(token = token, newerThan = _newerThan))
		Assert.assertNotNull(result)
		logger.debug { result }
		val dateNano = _newerThan.toEpochMilli() * 1000
		result!!.itemRefs.forEach {
			Assert.assertTrue(it.timestampUsec > dateNano)
		}
	}

	@Test
	fun testReadItemIds() {
		val result = api.readItemIds(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testReadItemContents() {
		val result = api.readItemContents(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testStarredItemIds() {
		val result = api.starredItemIds(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testStarredItemContents() {
		val result = api.starredItemContents(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testLikedItemIds() {
		val result = api.likedItemIds(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testLikedItemContents() {
		val result = api.likedItemContents(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testSharedItemIds() {
		val result = api.sharedItemIds(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testSharedItemContents() {
		val result = api.sharedItemContents(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testCommentedItemIds() {
		val result = api.commentedItemIds(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testCommentedItemContents() {
		val result = api.commentedItemContents(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testSharedByFriendsItemIds() {
		val result = api.sharedByFriendsItemIds(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testSharedByFriendsItemContents() {
		val result = api.sharedByFriendsItemContents(ItemRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testSubscriptionItems() {
		val result = api.subscriptionItems(SubscriptionItemRequest(token, _subscriptionId))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testSubscriptionItemContents() {
		val result = api.subscriptionItemContents(SubscriptionItemRequest(token, _subscriptionId))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testFriendItems() {
		val result = api.friendItems(FriendItemRequest(token, _friendId))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testFolderItems() {
		val result = api.folderItems(FolderItemRequest(token, _folder))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testItemContents() {
		val result = api.itemContents(ItemContentRequest(token, _itemContents))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testMarkSharedByFriendsAsRead() {
		val result = api.markSharedByFriendsAsRead(MarkAsReadRequest(token, _markOlderThan))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testMarkSharedByFriendAsRead() {
		val result = api.markSharedByFriendAsRead(FriendMarkAsReadRequest(token, _friendId, _markOlderThan))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testMarkFolderItemsAsRead() {
		val result = api.markFolderItemsAsRead(FolderMarkAsReadRequest(token, _folder))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testMarkAllAsRead() {
		val result = api.markAllAsRead(MarkAsReadRequest(token, _markOlderThan))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testMarkSubscriptionItemsAsRead() {
		val result = api.markSubscriptionItemsAsRead(SubscriptionMarkAsReadRequest(token, _subscriptionId,
				_markOlderThan))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testMarkAsStarred() {
		val result = api.markAsStarred(ItemContentRequest(token, _markAsStarred))
		Assert.assertNotNull(result)
		Assert.assertEquals(SUCCESS, result)
		logger.debug { result }

		val markedAsStarred = api.starredItemIds(ItemRequest(token))
		Assert.assertNotNull(markedAsStarred)
		logger.debug { markedAsStarred }
		val markedIds = markedAsStarred?.itemRefs!!.stream().map { id -> id.id }.collect(Collectors.toList())
		Assert.assertTrue(_markAsStarred.stream().allMatch { id -> markedIds.contains(id) })
	}

	@Test
	fun testRemoveStarredMark() {
		val result = api.removeStarredMark(ItemContentRequest(token, _markAsStarred))
		Assert.assertNotNull(result)
		Assert.assertEquals(SUCCESS, result)
		logger.debug { result }

		val markedAsStarred = api.starredItemIds(ItemRequest(token))
		Assert.assertNotNull(markedAsStarred)
		logger.debug { markedAsStarred }
		val markedIds = markedAsStarred?.itemRefs!!.stream().map { id -> id.id }.collect(Collectors.toList())
		Assert.assertTrue(_markAsStarred.stream().allMatch { id -> !markedIds.contains(id) })
	}

	@Test
	fun testMarkAsLiked() {
		val result = api.markAsLiked(ItemContentRequest(token, _markAsLiked))
		Assert.assertNotNull(result)
		Assert.assertEquals(SUCCESS, result)
		logger.debug { result }

		val markedAsLiked = api.likedItemIds(ItemRequest(token))
		Assert.assertNotNull(markedAsLiked)
		logger.debug { markedAsLiked }
		val markedIds = markedAsLiked?.itemRefs!!.stream().map { id -> id.id }.collect(Collectors.toList())
		Assert.assertTrue(_markAsLiked.stream().allMatch { id -> markedIds.contains(id) })
	}

	@Test
	fun testRemoveLikedMark() {
		val result = api.removeLikedMark(ItemContentRequest(token, _markAsLiked))
		Assert.assertNotNull(result)
		Assert.assertEquals(SUCCESS, result)
		logger.debug { result }

		val markedAsLiked = api.likedItemIds(ItemRequest(token))
		Assert.assertNotNull(markedAsLiked)
		logger.debug { markedAsLiked }
		val markedIds = markedAsLiked?.itemRefs!!.stream().map { id -> id.id }.collect(Collectors.toList())
		Assert.assertTrue(_markAsLiked.stream().allMatch { id -> !markedIds.contains(id) })
	}

	@Test
	fun testMarkAsShared() {
		val result = api.markAsShared(ItemContentRequest(token, _markAsShared))
		Assert.assertNotNull(result)
		Assert.assertEquals(SUCCESS, result)
		logger.debug { result }

		val markedAsShared = api.sharedItemIds(ItemRequest(token))
		Assert.assertNotNull(markedAsShared)
		logger.debug { "Shared: $markedAsShared" }
		val markedIds = markedAsShared?.itemRefs!!.stream().map { id -> id.id }.collect(Collectors.toList())
		Assert.assertTrue(_markAsShared.stream().allMatch { id -> markedIds.contains(id) })
	}

	@Test
	fun testRemoveSharedMark() {
		val result = api.removeSharedMark(ItemContentRequest(token, _markAsShared))
		Assert.assertNotNull(result)
		Assert.assertEquals(SUCCESS, result)
		logger.debug { result }

		val markedAsShared = api.sharedItemIds(ItemRequest(token))
		Assert.assertNotNull(markedAsShared)
		logger.debug { "Shared: $markedAsShared" }
		val markedIds = markedAsShared?.itemRefs!!.stream().map { id -> id.id }.collect(Collectors.toList())
		Assert.assertTrue(_markAsShared.stream().allMatch { id -> !markedIds.contains(id) })
	}

	@Test
	fun testMarkAsUnread() {
		val result = api.markAsUnread(ItemContentRequest(token, _markAsUnread))
		Assert.assertNotNull(result)
		Assert.assertEquals(SUCCESS, result)
		logger.debug { result }

		val markedAsUnread = api.allItemIds(ItemRequest(token = token, unread = true))
		Assert.assertNotNull(markedAsUnread)
		logger.debug { "Unread: $markedAsUnread" }
		val markedIds = markedAsUnread?.itemRefs!!.stream().map { id -> id.id }.collect(Collectors.toList())
		Assert.assertTrue(_markAsUnread.stream().allMatch { id -> markedIds.contains(id) })
	}
}
