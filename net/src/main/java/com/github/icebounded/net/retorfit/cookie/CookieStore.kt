package com.github.icebounded.net.retorfit

import android.content.Context
import android.content.SharedPreferences
import com.github.icebounded.base.util.clear
import com.github.icebounded.base.util.put
import com.github.icebounded.base.util.remove
import com.github.icebounded.net.retorfit.cookie.CookieSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import okhttp3.Cookie
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by liuyanxi
 * on 2021/3/2
 */
class CookieStore(context: Context, sp_name: String) {

    private val allCookies: ConcurrentHashMap<String, MutableMap<String, Cookie>> = ConcurrentHashMap()

    private val cookiePrefs: SharedPreferences =
        context.getSharedPreferences(sp_name, Context.MODE_PRIVATE)

    private val serializer = MapSerializer(String.serializer(), CookieSerializer())

    init {
        for ((k, v) in cookiePrefs.all) {
            allCookies[k] = Json.decodeFromString(serializer, v as String) as MutableMap<String, Cookie>
        }
    }

    fun add(host: String, cookie: Cookie) {
        val map = allCookies[host] ?: mutableMapOf<String, Cookie>().also {
            allCookies[host] = it
        }
        map[cookie.name] = cookie
        cookiePrefs.put(host, Json.encodeToString(serializer, map))
    }

    fun add(host: String, cookies: List<Cookie>) {
        val map = allCookies[host] ?: mutableMapOf<String, Cookie>().also {
            allCookies[host] = it
        }
        for (cookie in cookies) {
            map[cookie.name] = cookie
        }
        cookiePrefs.put(host, Json.encodeToString(serializer, map))
    }

    fun get(host: String?): List<Cookie> {
        return allCookies[host]?.values?.toList() ?: listOf()
    }

    fun clear() {
        allCookies.clear()
        cookiePrefs.clear()
    }

    fun remove(host: String) {
        allCookies.remove(host)
        cookiePrefs.remove(host)
    }

}