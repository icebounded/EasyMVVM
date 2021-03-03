package com.github.icebounded.base.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.Serializable

/**
 * Created by Andy
 * on 2021/3/1
 */

val defaultPrefs: SharedPreferences by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    application.getSharedPreferences("default", Context.MODE_PRIVATE)
}

inline fun <reified T> SharedPreferences.put(name: String, value: T) {
    edit(commit = true) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            is Serializable -> putString(name, serializeJava(value))
            else -> putString(name, Json.encodeToString(value))
        }
    }
}

fun <T> SharedPreferences.get(name: String, default: T?): T? {
    val res: Any? = when (default) {
        is Long -> getLong(name, default)
        is String -> getString(name, default)
        is Int -> getInt(name, default)
        is Boolean -> getBoolean(name, default)
        is Float -> getFloat(name, default)
        is Serializable -> deSerializationJava(getString(name, serializeJava(default)))
        else -> {
            val string = getString(name, null)
            string?.let { Json.decodeFromString(it) } ?: default
        }
    }
    return res as T
}

/**
 * 删除全部数据
 */
fun SharedPreferences.clear() {
    edit(commit = true) { clear() }
}

/**
 * 根据key删除存储数据
 */
fun SharedPreferences.remove(key: String) {
    edit(commit = true) { remove(key) }
}


