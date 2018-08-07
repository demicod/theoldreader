package com.github.demicod.theoldreader.model

data class FolderRequest(
		override val token: String,
		val id: String
) : BaseRequest(token)

data class RenameFolderRequest(
		override val token: String,
		val oldId: String,
		val newId: String
) : BaseRequest(token)

data class Folder(val id: String?, val sortid: String?)

data class FolderList(val tags: List<Folder>)
