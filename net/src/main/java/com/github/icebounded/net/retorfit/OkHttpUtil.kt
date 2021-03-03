package com.github.icebounded.net.retorfit

import com.github.icebounded.net.ssl.UnSafeTrustManager
import com.github.icebounded.net.ssl.getSslSocketFactory
import okhttp3.OkHttpClient

/**
 * Created by liuyanxi
 * on 2021/3/1
 */
class OkHttpUtil private constructor() {

    companion object {
        val instance: OkHttpUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkHttpUtil()
        }
    }

    private fun build(): OkHttpClient {
        val unSafeTrustManager = UnSafeTrustManager()
        return OkHttpClient.Builder().run {
//            cookieJar(CookieJarImpl(mStore))
//            connectTimeout(NetConfig.OKHTTP_CONNECTTIMEOUT, TimeUnit.SECONDS)
//            readTimeout(NetConfig.OKHTTP_READTIMEOUT, TimeUnit.SECONDS)
//            writeTimeout(NetConfig.OKHTTP_READTIMEOUT, TimeUnit.SECONDS)
//            addInterceptor(HttpCommonInterceptor())
            sslSocketFactory(
                getSslSocketFactory(unSafeTrustManager),
                UnSafeTrustManager()
            )
        }.build()

    }
}