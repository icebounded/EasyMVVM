package pers.icebounded.net.response

import org.json.JSONObject
import pers.icebounded.net.R
import pers.icebounded.net.exception.ApiException
import pers.icebounded.net.exception.ErrorType

/**
 * Created by Andy
 * on 2021/3/4
 */
class ApiResponse<Data : ApiData> {

    var code: Int = 0
    var message: String = ""
    var exception: Exception? = null

    lateinit var data: Data

    fun parse(jsonObject: JSONObject) {
        code = jsonObject.optInt("code", 1)
        message = jsonObject.optString("message")
        if (isSuccess()) {
            kotlin.runCatching {
                data.parse(jsonObject.optJSONObject("data"), jsonObject)
            }.onFailure {
                ApiException(ErrorType.PARSE, 0, R.string.net_error_parse, it)
            }
        } else {
            throw ApiException(ErrorType.API, code, message, null)
        }
    }

    inline fun success(crossinline success: ApiResponse<Data>.() -> Unit): ApiResponse<Data> {
        if (null == exception) {
            success.invoke(this)
        }
        return this
    }

    inline fun error(crossinline error: Exception.() -> Unit): ApiResponse<Data> {
        exception?.let(error)
        return this
    }

    open fun isSuccess() = code == 1
}
