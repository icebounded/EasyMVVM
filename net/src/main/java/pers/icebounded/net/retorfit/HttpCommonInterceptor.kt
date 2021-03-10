package pers.icebounded.net.retorfit

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Andy
 * on 2021/3/3
 */
class HttpCommonInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val urlBuilder: HttpUrl.Builder = request.url.newBuilder()
            .addQueryParameter("apikey", "4e3eaf74-b1c4-4b04-9e14-d9d9d55737ae")

        val requestBuilder = request.newBuilder()

        requestBuilder.url(urlBuilder.build())

        return chain.proceed(requestBuilder.build())
    }

}