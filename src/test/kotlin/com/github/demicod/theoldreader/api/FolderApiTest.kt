package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.model.BaseRequest
import com.github.demicod.theoldreader.model.FolderRequest
import com.github.demicod.theoldreader.model.RenameFolderRequest
import org.junit.Assert
import org.junit.Test

class FolderApiTest : BaseApiTest() {

	private val api: FolderApi = FolderApi(config)

	private val _oldFolderId = "user/-/label/Test2"
	private val _newFolderId = "user/-/label/Test3"
	private val _disableFolderId = "user/-/label/Test3"

	@Test
	fun testFolderList() {
		val result = api.folderList(BaseRequest(token))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testRenameFolder() {
		val result = api.renameFolder(RenameFolderRequest(token, _oldFolderId, _newFolderId))
		Assert.assertNotNull(result)
		logger.debug { result }
	}

	@Test
	fun testDisableFolder() {
		val result = api.disableFolder(FolderRequest(token, _disableFolderId))
		Assert.assertNotNull(result)
		logger.debug { result }
	}
}
