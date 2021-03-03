package com.github.icebounded.net.retorfit.cookie

import com.github.icebounded.base.util.application
import com.github.icebounded.net.retorfit.CookieStore
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Created by liuyanxi
 * on 2021/3/2
 */
class CookieManager : CookieJar {

    var cookieStore: CookieStore = CookieStore(application, "cookie")

    companion object {
        val instance: CookieManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CookieManager()
        }
    }

    /**
     * 优先取手机号登录的Cookie
     */
    fun getCookies(host: String) = cookieStore.get(host)

    /**
     * 删除所有的cookie
     */
    fun clearCookie() = cookieStore.clear()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) =
        cookieStore.add(url.host, cookies)

    override fun loadForRequest(url: HttpUrl) = getCookies(url.host)

}