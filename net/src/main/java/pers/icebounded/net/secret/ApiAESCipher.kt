package pers.icebounded.net.secret

import android.util.Base64
import java.security.MessageDigest
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by Andy
 * on 2021/3/8
 */
class ApiAESCipher(private val key: String) {

    // 加密key
    private val secretKeySpec: SecretKeySpec

    // 加密方式
    private val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

    // 偏移量
    private val spec: AlgorithmParameterSpec = IvParameterSpec(ByteArray(16))

    private val charset = Charsets.UTF_8

    init {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        digest.update(key.toByteArray(charset))
        val keyBytes = ByteArray(32)
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.size)
        secretKeySpec = SecretKeySpec(keyBytes, "AES")
    }

    @Throws(Exception::class)
    fun encrypt(plainText: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, spec)
        val encrypted = cipher.doFinal(plainText.toByteArray(charset))
        return String(Base64.encode(encrypted, Base64.NO_WRAP), charset)
    }

    @Throws(Exception::class)
    fun decrypt(cryptedText: String?): String {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, spec)
        val bytes: ByteArray = Base64.decode(cryptedText, Base64.NO_WRAP)
        val decrypted = cipher.doFinal(bytes)
        return String(decrypted, charset)
    }

}
