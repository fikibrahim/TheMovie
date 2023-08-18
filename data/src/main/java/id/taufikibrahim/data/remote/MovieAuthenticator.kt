package id.taufikibrahim.data.remote

import id.taufikibrahim.data.BuildConfig
import okhttp3.*
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class MovieAuthenticator: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        try {
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            val requestBuilder = original.newBuilder()
                .url(url)
            val request = requestBuilder.build()
            return chain.proceed(request)
        } catch (e: Exception) {
            e.printStackTrace()
            val msg = when (e) {
                is SocketTimeoutException -> {
                    "Timeout - Please check your internet connection"
                }
                is UnknownHostException -> {
                    "Unable to make a connection. Please check your internet"
                }
                is ConnectionShutdownException -> {
                    "Connection shutdown. Please check your internet"
                }
                is IOException -> {
                    "Server is unreachable, please try again later."
                }
                else -> (e as? IllegalStateException)?.message ?: e.message.toString()
            }
            return Response.Builder().request(original).protocol(Protocol.HTTP_1_1)
                .code(900).message(msg).body(
                    ResponseBody.create(
                        null as MediaType?,
                        StringBuilder().append('{').append(e).append('}').toString()
                    )
                ).build()
        }
    }
}
