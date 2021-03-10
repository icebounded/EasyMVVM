package pers.icebounded.net.retorfit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import pers.icebounded.net.NetConfig
import pers.icebounded.net.convert.CustomConverterFactory
import pers.icebounded.net.cookie.CookieManager
import pers.icebounded.net.exception.handleException
import pers.icebounded.net.response.ApiResponse
import pers.icebounded.net.ssl.UnSafeTrustManager
import pers.icebounded.net.ssl.getSslSocketFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Andy
 * on 2021/2/28
 */

inline fun <reified T> createHttpService(): T {
    return retrofit.create(T::class.java)
}

suspend inline fun <reified T, reified R : ApiResponse<T>> requestHttp(request: suspend () -> R): R {
    return try {
        request.invoke()
    } catch (e: Throwable) {
        R::class.java.newInstance().apply {
            exception = handleException(e)
        }
    }
}

val retrofit: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    Retrofit.Builder().run {
        client(okHttpClient())
        baseUrl(NetConfig.getBaseUrl())
        addConverterFactory(CustomConverterFactory())
        addConverterFactory(Json.asConverterFactory("application/json; charset=UTF-8".toMediaType())) //.addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
        addConverterFactory(GsonConverterFactory.create()) //.addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
        build()
    }
}

fun okHttpClient(): OkHttpClient {
    val unSafeTrustManager = UnSafeTrustManager()
    return OkHttpClient.Builder().run {
        cookieJar(CookieManager())
        connectTimeout(NetConfig.OK_HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(NetConfig.OK_HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(NetConfig.OK_HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(HttpCommonInterceptor())
        sslSocketFactory(
            getSslSocketFactory(unSafeTrustManager),
            UnSafeTrustManager()
        )
    }.build()
}