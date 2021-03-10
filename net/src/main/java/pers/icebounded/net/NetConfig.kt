package pers.icebounded.net

/**
 * Created by Andy
 * on 2021/3/1
 */
class NetConfig {
    companion object {
        const val OK_HTTP_CONNECT_TIMEOUT = 15L
        const val OK_HTTP_READ_TIMEOUT = 15L

        fun getBaseUrl() : String {
            return "https://api.harvardartmuseums.org"
        }

    }

}

