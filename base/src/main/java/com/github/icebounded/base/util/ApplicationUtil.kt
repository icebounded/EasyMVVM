package com.github.icebounded.base.util

/**
 * Created by Andy
 * on 2021/3/1
 */
import android.annotation.SuppressLint
import android.content.Context

val application: Context
    get() = appContext ?: initAndGetAppCtxWithReflection()

@SuppressLint("StaticFieldLeak")
private var appContext: Context? = null

/**
 * This method will return true on [Context] implementations known to be able to leak memory.
 * This includes [Activity], [Service], the lesser used and lesser known [BackupAgent], as well as
 * any [ContextWrapper] having one of these as its base context.
 */
private fun initAndGetAppCtxWithReflection(): Context {
    // Fallback, should only run once per non default process.
    val activityThread = Class.forName("android.app.ActivityThread")
    val ctx = activityThread.getDeclaredMethod("currentApplication").invoke(null) as Context
    appContext = ctx
    return ctx
}
