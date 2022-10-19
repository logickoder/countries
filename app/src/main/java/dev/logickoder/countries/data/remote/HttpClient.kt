package dev.logickoder.countries.data.remote

import dev.logickoder.countries.utils.ResultWrapper
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal val RemoteClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json {
            encodeDefaults = true
            prettyPrint = true
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Napier.v(message)
            }
        }
        level = LogLevel.BODY
    }
    install(HttpTimeout) {
        val timeout = 10_000L
        socketTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        connectTimeoutMillis = timeout
    }
    install(DefaultRequest) {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }
}

suspend fun <T> HttpClient.call(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend HttpClient.() -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(this@call.apiCall())
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Napier.e(e.response.status.description)
            ResultWrapper.Failure(e)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Napier.e(e.response.status.description)
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            // 5xx - responses
            Napier.e(e.response.status.description)
            ResultWrapper.Failure(e)
        } catch (throwable: Throwable) {
            Napier.e(throwable.message.toString())
            ResultWrapper.Failure(throwable)
        }
    }
}