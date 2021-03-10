package pers.icebounded.lib.util

/**
 * Created by Andy
 * on 2021/3/1
 */
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo

import android.content.pm.PackageManager
import android.provider.Settings


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

/**
 * 获取手机系统SDK版本
 *
 * @return
 */
fun getSDKVersion(): Int = android.os.Build.VERSION.SDK_INT

fun versionName(): String {
    val pm: PackageManager = application.packageManager
    val pi: PackageInfo = pm.getPackageInfo(application.packageName, 0)
    return pi.versionName
}

@Suppress("DEPRECATION")
fun versionCode(): Long {
    val pm: PackageManager = application.packageManager
    val pi: PackageInfo = pm.getPackageInfo(application.packageName, 0)
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        pi.longVersionCode
    } else {
        pi.versionCode.toLong()
    }
}

fun androidId(): String {
    return Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID);
}

/**
 * 获取设备的可用内存大小,单位是M
 *
 * @param context 应用上下文对象context
 * @return 当前内存大小
 */
fun getDeviceUsableMemory(context: Context): Long {
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val mi = ActivityManager.MemoryInfo()
    am.getMemoryInfo(mi)
    // 返回当前系统的可用内存
    return mi.availMem / (1024 * 1024)
}



