package com.github.demicod.theoldreader.api

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.MapperFeature
import com.github.demicod.theoldreader.Config
import com.github.demicod.theoldreader.exception.ApiException
import com.github.demicod.theoldreader.model.ErrorList
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpDownload
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import com.github.kittinunf.fuel.jackson.mapper
import com.github.kittinunf.result.Result
import mu.KLogging
import java.io.File
import java.net.HttpURLConnection

const val BASE_API_URI = "/reader/api/0"

const val SUCCESS = "OK"

const val DEFAULT_FILENAME = "theoldreader"
const val DEFAULT_SUFFIX = ".tmp"

abstract class BaseApi(protected val config: Config) : KLogging() {

	init {
		with(FuelManager.instance) {
			basePath = config.basePath
		}
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
	}

	protected inline fun <reified T : Any> doGet(uri: String, parameters: List<Pair<String, Any>>? = null): T? {
		return doGet(null, uri, parameters)
	}

	protected inline fun <reified T : Any> doGet(token: String?, uri: String, parameters: List<Pair<String, Any>>? =
			null): T? {
		logger.debug { "doGet" }
		val stringResult = T::class == String::class
		val mutableParameters = parameters?.toMutableList() ?: mutableListOf()
		if (!stringResult) {
			mutableParameters.add(outputToJson())
		}
		val tokenHeader = if (token != null) tokenHeader(token) else null
		val (request, response, requestResult) = uri
				.httpGet(mutableParameters)
				.header(tokenHeader)
				.response()
		logger.debug { "Request: $request" }
		logger.debug { "Response: $response" }
		return handleResponse(response, requestResult) {
			if (stringResult) String(response.data) as T
			else jacksonDeserializerOf<T>().deserialize(response.data)
		}
	}

	protected inline fun <reified T : Any> doPost(uri: String, parameters: List<Pair<String, Any>>): T? {
		return doPost(null, uri, parameters)
	}

	protected inline fun <reified T : Any> doPost(token: String?, uri: String, parameters: List<Pair<String, Any>>): T? {
		logger.debug { "doPost" }
		val mutableParameters = parameters.toMutableList()
		val stringResult = T::class == String::class
		if (!stringResult) {
			mutableParameters.add(outputToJson())
		}
		val tokenHeader = if (token != null) tokenHeader(token) else null
		val (request, response, requestResult) = uri
				.httpPost(mutableParameters)
				.header(tokenHeader)
				.response()
		logger.debug { "Request: $request" }
		logger.debug { "Response: $response" }
		return handleResponse(response, requestResult) {
			if (stringResult) {
				if (String(response.data) == SUCCESS) {
					SUCCESS as T
				} else {
					try {
						val error = jacksonDeserializerOf<ErrorList>().deserialize(response.data)
						throw ApiException(error?.errors?.reduce { a, b -> "$a; $b" }.orEmpty())
					} catch (e: JsonParseException) {
						String(response.data) as T
					}
				}
			} else {
				jacksonDeserializerOf<T>().deserialize(response.data)
			}
		}
	}

	protected fun doDownload(token: String, uri: String, outputDir: String?): String? {
		var outputFile: File? = null
		var fileName: String? = null
		val directory: File? = if (outputDir != null) File(outputDir) else null
		val (request, response, requestResult) = uri
				.httpDownload()
				.header(tokenHeader(token))
				.destination { res, _ ->
					val name = attachmentName(res.headers)
					val tempFile = File.createTempFile(tempFilePrefix(name), DEFAULT_SUFFIX, directory)
					fileName = name
					outputFile = tempFile
					tempFile
				}.response()
		logger.debug { "Request: $request" }
		logger.debug { "Response: $response" }
		return handleResponse(response, requestResult) {
			if (!fileName.isNullOrBlank() && outputFile != null) {
				val newFile = outputFile!!.resolveSibling(fileName!!)
				if (outputFile!!.renameTo(newFile)) {
					newFile.absolutePath
				} else {
					outputFile!!.absolutePath
				}
			} else {
				null
			}
		}
	}

	protected fun tokenHeader(token: String): Pair<String, Any> {
		return "Authorization" to "GoogleLogin auth=$token"
	}

	protected fun outputToJson(): Pair<String, Any> {
		return "output" to "json"
	}

	private fun attachmentName(headers: Map<String, List<String>>): String {
		val header = headers["Content-Disposition"]
		header?.component1()?.let {
			val regex = Regex("filename=\"(.*?)\"")
			val matchResult = regex.find(header.component1())
			return matchResult?.destructured?.component1().orEmpty()
		}
		return ""
	}

	private fun tempFilePrefix(name: String?): String {
		return if (name != null && name.isNotBlank()) name else "$DEFAULT_FILENAME-"
	}

	private fun handleResponseError(result: Result<ByteArray, FuelError>): Nothing {
		val t = result.component2()?.cause
		throw ApiException("Error: " + (t?.let { t.message }), t)
	}

	protected fun <T : Any> handleResponse(
			response: Response, result: Result<ByteArray, FuelError>, success: () -> T?): T? {
		return when (response.statusCode) {
			HttpURLConnection.HTTP_OK -> success()
			else -> handleResponseError(result)
		}
	}
}
