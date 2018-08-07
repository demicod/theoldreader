package com.github.demicod.theoldreader

import com.github.demicod.theoldreader.api.BaseApiTest
import com.github.demicod.theoldreader.model.BaseRequest
import com.github.demicod.theoldreader.model.ClientLoginRequest
import com.github.demicod.theoldreader.model.ItemRequest
import org.junit.Assert
import org.junit.Test

class TheoldreaderTest : BaseApiTest() {

	private val theoldreader = com.github.demicod.theoldreader.Theoldreader(config)

	private val email = "somebody@somewhere.net"
	private val password = "password"

	@Test
	fun testServerStatus() {
		with(theoldreader) {
			val status = status()
			Assert.assertNotNull(status)
			Assert.assertEquals("up", status!!.status)
			logger.debug { status }
		}
	}

	@Test
	fun testLogin() {
		with(theoldreader) {
			val clientLogin = auth.login(ClientLoginRequest(email, password))
			Assert.assertNotNull(clientLogin)
			logger.debug { clientLogin }

			val token = clientLogin!!.Auth
			Assert.assertNotNull(clientLogin)
			logger.debug { token }
		}
	}

	@Test
	fun testGetUserInfo() {
		with(theoldreader) {
			val clientLogin = auth.login(ClientLoginRequest(email, password))
			Assert.assertNotNull(clientLogin)
			logger.debug { clientLogin }

			val token = clientLogin!!.Auth
			Assert.assertNotNull(clientLogin)
			logger.debug { token }

			val userInfo = users.info(BaseRequest(token))
			Assert.assertNotNull(userInfo)
			logger.debug { "User info: $userInfo" }
		}
	}

	@Test
	fun testListSubscriptions() {
		with(theoldreader) {
			val clientLogin = auth.login(ClientLoginRequest(email, password))
			Assert.assertNotNull(clientLogin)
			logger.debug { clientLogin }

			val token = clientLogin!!.Auth
			Assert.assertNotNull(clientLogin)
			logger.debug { token }

			val subscriptions = subscriptions.subscriptionList(BaseRequest(token))
			Assert.assertNotNull(subscriptions)
			logger.debug { "Subscriptions: $subscriptions" }
		}
	}

	@Test
	fun testGetUnreadItemIds() {
		with(theoldreader) {
			val clientLogin = auth.login(ClientLoginRequest(email, password))
			Assert.assertNotNull(clientLogin)
			logger.debug { clientLogin }

			val token = clientLogin!!.Auth
			Assert.assertNotNull(clientLogin)
			logger.debug { token }

			val itemIds = items.allItemIds(ItemRequest(token = token, unread = true))
			Assert.assertNotNull(itemIds)
			logger.debug { "Unread item ids: $itemIds" }
		}
	}

	@Test
	fun testListFolders() {
		with(theoldreader) {
			val clientLogin = auth.login(ClientLoginRequest(email, password))
			Assert.assertNotNull(clientLogin)
			logger.debug { clientLogin }

			val token = clientLogin!!.Auth
			Assert.assertNotNull(clientLogin)
			logger.debug { token }

			val folders = folders.folderList(BaseRequest(token))
			Assert.assertNotNull(folders)
			logger.debug { "Folders: $folders" }
		}
	}

	@Test
	fun testListFriends() {
		with(theoldreader) {
			val clientLogin = auth.login(ClientLoginRequest(email, password))
			Assert.assertNotNull(clientLogin)
			logger.debug { clientLogin }

			val token = clientLogin!!.Auth
			Assert.assertNotNull(clientLogin)
			logger.debug { token }

			val friends = friends.friendList(BaseRequest(token))
			Assert.assertNotNull(friends)
			logger.debug { "Friends: $friends" }
		}
	}
}
