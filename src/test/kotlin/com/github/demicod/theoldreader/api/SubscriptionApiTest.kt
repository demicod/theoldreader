package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.model.AddSubscriptionRequest
import com.github.demicod.theoldreader.model.BaseRequest
import com.github.demicod.theoldreader.model.ExportSubscriptionRequest
import com.github.demicod.theoldreader.model.RemoveSubscriptionRequest
import com.github.demicod.theoldreader.model.UpdateSubscriptionRequest
import org.junit.Assert
import org.junit.Test

class SubscriptionApiTest : BaseApiTest() {

	private val api: SubscriptionApi = SubscriptionApi(config)

	private val _outputDir = "C:\\Temp"
	private val _subscriptionUrl = "https://news.google.com/news/rss/headlines/section/topic/TECHNOLOGY?ned=us&hl=en&gl=US"
	private val _updateSubscriptionId = "feed/5b62dabbfea0e769d8000bc8"
	private val _updateSubscriptionFolderId = "user/-/label/Test2"
	private val _removeSubcriptionId = "feed/5b62dabbfea0e769d8000bc8"
	private val _updateSubscriptionTitle = "Update Subscription Test"

	@Test
	fun testUnreadCount() {
		val result = api.unreadCount(BaseRequest(token))
		logger.debug { result }
		Assert.assertNotNull(result)
	}

	@Test
	fun testSubscriptionList() {
		val result = api.subscriptionList(BaseRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testExportOPML() {
		val result = api.exportOPML(ExportSubscriptionRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testExportOPMLToDirectory() {
		val result = api.exportOPML(ExportSubscriptionRequest(token, _outputDir))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testAddSubscription() {
		val result = api.addSubscription(AddSubscriptionRequest(token, _subscriptionUrl))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testUpdateSubscription() {
		val result = api.updateSubscription(UpdateSubscriptionRequest(token, _updateSubscriptionId,
				_updateSubscriptionTitle, _updateSubscriptionFolderId))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testRemoveSubscription() {
		val result = api.removeSubscription(RemoveSubscriptionRequest(token, _removeSubcriptionId))
		Assert.assertNotNull(result)
		logger.debug { result }
	}
}
