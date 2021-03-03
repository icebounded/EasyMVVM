package com.github.icebounded.net.retorfit

import android.content.Context
import androidx.core.content.edit
import com.github.icebounded.base.util.application
import com.github.icebounded.net.ssl.UnSafeTrustManager
import com.github.icebounded.net.ssl.getSslSocketFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by liuyanxi
 * on 2021/2/28
 */
class RetrofitUtil private constructor() {

    val retrofit : Retrofit

    companion object {
        val instance: RetrofitUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitUtil()
        }
    }

    init {
        application.getSharedPreferences("spName", Context.MODE_PRIVATE).edit(commit = true) {
            putString("1", "1")
        }

        retrofit = Retrofit.Builder().run {
            client(okHttpClient())
            addConverterFactory(GsonConverterFactory.create()) //.addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
            build()
        }
    }

    fun okHttpClient(): OkHttpClient {
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