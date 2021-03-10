package pers.icebounded.lib.ext

import android.util.Log
import pers.icebounded.lib.BuildConfig

/**
 * Created by Andy
 * on 2021/2/25
 */

const val TAG = "ktx"

val showLog = BuildConfig.DEBUG
val showStackTrace = false

private enum class LEVEL {
    V, D, I, W, E
}

fun String.logv(tag: String = TAG): String = log(LEVEL.V, tag, this)
fun String.logd(tag: String = TAG): String = log(LEVEL.D, tag, this)
fun String.logi(tag: String = TAG): String = log(LEVEL.I, tag, this)
fun String.logw(tag: String = TAG): String = log(LEVEL.W, tag, this)
fun String.loge(tag: String = TAG): String = log(LEVEL.E, tag, this)

fun logv(tag: String = TAG, message: Any): String = log(LEVEL.V, tag, message.toString())
fun logd(tag: String = TAG, message: Any): String = log(LEVEL.D, tag, message.toString())
fun logi(tag: String = TAG, message: Any): String = log(LEVEL.I, tag, message.toString())
fun logw(tag: String = TAG, message: Any): String = log(LEVEL.W, tag, message.toString())
fun loge(tag: String = TAG, message: Any): String = log(LEVEL.E, tag, message.toString())

private fun log(level: LEVEL, tag: String, message: String): String {
    if (!showLog) return message

    val tagBuilder = StringBuilder()
    tagBuilder.append(tag)

    if (showStackTrace) {
        val stackTrace = Thread.currentThread().stackTrace[5]
        tagBuilder.append(" ${stackTrace.methodName}(${stackTrace.fileName}:${stackTrace.lineNumber})")
    }
    when (level) {
        LEVEL.V -> Log.v(tagBuilder.toString(), message)
        LEVEL.D -> Log.d(tagBuilder.toString(), message)
        LEVEL.I -> Log.i(tagBuilder.toString(), message)
        LEVEL.W -> Log.w(tagBuilder.toString(), message)
        LEVEL.E -> Log.e(tagBuilder.toString(), message)
    }
    return message
}