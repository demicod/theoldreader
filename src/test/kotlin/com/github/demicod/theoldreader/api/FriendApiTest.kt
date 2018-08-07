package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.model.BaseRequest
import com.github.demicod.theoldreader.model.FriendRequest
import org.junit.Assert
import org.junit.Test

class FriendApiTest : BaseApiTest() {

	private val api = FriendApi(config)

	private val _followFriendId = "3fed8b50692fd1f72eb6bb5a"
	private val _unfollowFriendId = "3fed8b50692fd1f72eb6bb5a"

	@Test
	fun testFriendList() {
		val result = api.friendList(BaseRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testFollow() {
		val result = api.follow(FriendRequest(token, _followFriendId))
		Assert.assertNotNull(result)
		Assert.assertEquals(SUCCESS, result)
		logger.debug { result }
	}

	@Test
	fun testUnfollow() {
		val result = api.unfollow(FriendRequest(token, _unfollowFriendId))
		Assert.assertNotNull(result)
		Assert.assertEquals(SUCCESS, result)
		logger.debug { result }
	}
}
