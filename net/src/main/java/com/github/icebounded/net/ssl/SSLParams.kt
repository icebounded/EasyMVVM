package com.github.icebounded.net.ssl

import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

/**
 * Created by Andy
 * on 2021/3/1
 */
class SSLParams {
    var sSLSocketFactory: SSLSocketFactory? = null
    var trustManager: X509TrustManager? = null
}