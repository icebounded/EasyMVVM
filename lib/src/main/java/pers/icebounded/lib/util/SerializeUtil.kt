package pers.icebounded.lib.util

import java.io.*

/**
 * Created by Andy
 * on 2021/3/3
 */
/**
 * 序列化对象
 * @param person
 * *
 * @return
 * *
 * @throws IOException
 */
@Throws(IOException::class)
inline fun <reified T> serializeJava(obj: T): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val objectOutputStream = ObjectOutputStream(
        byteArrayOutputStream
    )
    objectOutputStream.writeObject(obj)
    var serStr = byteArrayOutputStream.toString("ISO-8859-1")
    serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
    objectOutputStream.close()
    byteArrayOutputStream.close()
    return serStr
}

/**
 * 反序列化对象
 * @param str
 * *
 * @return
 * *
 * @throws IOException
 * *
 * @throws ClassNotFoundException
 */
@Suppress("UNCHECKED_CAST")
@Throws(IOException::class, ClassNotFoundException::class)
inline fun <reified T> deSerializationJava(str: String?): T {
    val redStr = java.net.URLDecoder.decode(str, "UTF-8")
    val byteArrayInputStream = ByteArrayInputStream(
        redStr.toByteArray(charset("ISO-8859-1"))
    )
    val objectInputStream = ObjectInputStream(
        byteArrayInputStream
    )
    val obj = objectInputStream.readObject() as T
    objectInputStream.close()
    byteArrayInputStream.close()
    return obj
}