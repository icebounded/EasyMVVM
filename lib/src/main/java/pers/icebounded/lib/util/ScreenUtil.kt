package pers.icebounded.lib.util

/**
 * Created by Andy
 * on 2021/3/5
 */

/**
 * dp--px
 */
fun dp2px(dp: Float): Int {
    val scale = application.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun px2dp(px: Float): Float {
    val scale = application.resources.displayMetrics.density
    return (px / scale + 0.5f)
}

/**
 *  px--sp
 */
fun px2sp(px: Float): Int {
    val fontScale = application.resources.displayMetrics.scaledDensity
    return (px / fontScale + 0.5f).toInt()
}

fun sp2px(sp: Float): Float {
    val fontScale = application.resources.displayMetrics.scaledDensity
    return (sp * fontScale + 0.5f)
}

/**
 * The absolute width/height of the available display size in pixels
 */
fun screenWidth(): Int {
    return application.resources.displayMetrics.widthPixels
}

fun screenHeight(): Int {
    return application.resources.displayMetrics.heightPixels
}
