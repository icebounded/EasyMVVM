package pers.icebounded.lib.util

import android.os.Build
import android.text.Html
import android.util.Base64
import java.net.URLDecoder
import java.net.URLEncoder
import java.security.MessageDigest

/**
 * Created by Andy
 * on 2021/3/5
 */


/**
 * Return the urlencoded string.
 */

fun String.toURL(charsetName: String? = "UTF-8"): String {
    return runCatching {
        URLEncoder.encode(this, charsetName)
    }.getOrNull() ?: this
}

/**
 * Return the string of decode urlencoded string.
 */
@JvmOverloads
fun String.fromURL(charsetName: String? = "UTF-8"): String {
    return runCatching {
        val safeInput = this.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25")
            .replace("\\+".toRegex(), "%2B")
        URLDecoder.decode(safeInput, charsetName)
    }.getOrNull() ?: this
}

/**
 * Return Base64-encode bytes.
 */
fun ByteArray.toBase64String(): String {
    return if (isEmpty()) {
        ""
    } else {
        String(Base64.encode(this, Base64.NO_WRAP), Charsets.UTF_8)
    }
}

/**
 * Return Base64-encode bytes.
 */
fun ByteArray.toBase64(input: ByteArray): ByteArray {
    return if (input.isEmpty()) {
        ByteArray(0)
    } else {
        Base64.encode(input, Base64.NO_WRAP)
    }
}

/**
 * Return the bytes of decode Base64-encode string.
 */
fun ByteArray.fromBase64(): ByteArray {
    return if (isEmpty()) {
        ByteArray(0)
    } else {
        Base64.decode(this, Base64.NO_WRAP)
    }
}

/**
 * Return html-encode string.
 */
fun String.toHtml(): String {
    if (isEmpty()) {
        return ""
    }
    val sb = StringBuilder()
    var c: Char
    var i = 0
    while (i < length) {
        c = this[i]
        when (c) {
            '<' -> sb.append("&lt;") //$NON-NLS-1$
            '>' -> sb.append("&gt;") //$NON-NLS-1$
            '&' -> sb.append("&amp;") //$NON-NLS-1$
            '\'' -> sb.append("&#39;") //$NON-NLS-1$
            '"' -> sb.append("&quot;") //$NON-NLS-1$
            else -> sb.append(c)
        }
        i++
    }
    return sb.toString()
}

/**
 * Return the string of decode html-encode string.
 */
@Suppress("DEPRECATION")
fun String.fromHtml(): CharSequence {
    if(isEmpty()) {
        return ""
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

/**
 * Return the binary encoded string padded with one space
 */
fun String.toBinaryString(): String {
    val stringBuilder = StringBuilder()
    for (i in toCharArray()) {
        stringBuilder.append(Integer.toBinaryString(i.toInt()))
        stringBuilder.append(' ')
    }
    return stringBuilder.toString()
}

/**
 * Return UTF-8 String from binary
 */
fun String.fromBinary(input: String): String {
    val splitArray = input.split(" ").toTypedArray()
    val sb = StringBuilder()
    for (i in splitArray) {
        sb.append(i.replace(" ", "").toInt(2).toChar())
    }
    return sb.toString()
}

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}
