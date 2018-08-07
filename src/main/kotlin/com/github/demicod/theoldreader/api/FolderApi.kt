package com.github.demicod.theoldreader.api

import com.github.demicod.theoldreader.Config
import com.github.demicod.theoldreader.model.BaseRequest
import com.github.demicod.theoldreader.model.FolderList
import com.github.demicod.theoldreader.model.FolderRequest
import com.github.demicod.theoldreader.model.RenameFolderRequest

const val FOLDER_LIST_URI = "$BASE_API_URI/tag/list"
const val RENAME_FOLDER_URI = "$BASE_API_URI/rename-tag"
const val DISABLE_FOLDER_URI = "$BASE_API_URI/disable-tag"

class FolderApi(config: Config) : BaseApi(config) {

	fun folderList(request: BaseRequest): FolderList? {
		return doGet(request.token, FOLDER_LIST_URI)
	}

	fun renameFolder(request: RenameFolderRequest): String? {
		val parameters = listOf(
				"s" to request.oldId,
				"dest" to request.newId)
		return doPost(request.token, RENAME_FOLDER_URI, parameters)
	}

	fun disableFolder(request: FolderRequest): String? {
		val parameters = listOf(
				"s" to request.id
		)
		return doPost(request.token, DISABLE_FOLDER_URI, parameters)
	}
}
