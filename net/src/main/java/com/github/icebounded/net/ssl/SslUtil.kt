package com.github.icebounded.net.ssl

import java.io.BufferedInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.*

/**
 * Created by Andy
 * on 2021/3/1
 */

fun getSslSocketFactory(trustManager: X509TrustManager?): SSLSocketFactory {
    return getSslSocketFactory(trustManager, null, null)
}

fun getSslSocketFactory(
    trustManager: X509TrustManager?,
    bksFile: InputStream?,
    password: String?,
): SSLSocketFactory {

    val prepareKeyManager = prepareKeyManager(bksFile, password)

    //优先使用用户自定义的TrustManager
    var manager = trustManager ?: UnSafeTrustManager()

    // Create an SSLContext that uses our TrustManager
    val context: SSLContext = SSLContext.getInstance("TLS").apply {
        init(prepareKeyManager, arrayOf(manager), null)
    }

    return context.socketFactory
}

fun prepareKeyManager(bksFile: InputStream?, password: String?): Array<KeyManager>? {
    if (bksFile == null || password == null) return null
    bksFile.use {
        val clientKeyStore = KeyStore.getInstance("BKS")
        clientKeyStore.load(bksFile, password.toCharArray())
        return KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()).run {
            init(clientKeyStore, password.toCharArray())
            keyManagers
        }
    }
}

fun readTrustManager(certificates: Array<InputStream>?): Array<TrustManager>? {

    if (null == certificates) return null

    // Load CAs from an InputStream
    // (could be from a resource or ByteArrayInputStream or ...)
    val cf: CertificateFactory = CertificateFactory.getInstance("X.509")

    // Create a KeyStore containing our trusted CAs
    val keyStoreType = KeyStore.getDefaultType()

    val keyStore = KeyStore.getInstance(keyStoreType).apply {
//        load(null, null)
        for ((index, certificate) in certificates.withIndex()) {
            // From https://www.washington.edu/itconnect/security/ca/load-der.crt
            val caInput: InputStream = BufferedInputStream(certificate)
            val ca: Certificate = caInput.use {
                cf.generateCertificate(it)
            }
            setCertificateEntry(index.inc().toString(), ca)
        }
    }

    // Create a TrustManager that trusts the CAs inputStream our KeyStore
    return TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).run {
        init(keyStore)
        trustManagers
    }
}

fun firstX509TrustManager(trustManagers: Array<TrustManager>?): X509TrustManager? {
    return trustManagers?.firstOrNull {
        it is X509TrustManager
    } as X509TrustManager?
}