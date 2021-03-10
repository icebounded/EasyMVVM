package pers.icebounded.net.exception

import android.net.ParseException
import androidx.annotation.StringRes
import com.google.gson.JsonParseException
import org.json.JSONException
import pers.icebounded.lib.util.application
import pers.icebounded.net.R
import retrofit2.HttpException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Andy
 * on 2021/3/4
 */
class ApiException(
    val errorType: ErrorType,
    val code: Int,
    msg: String,
    exception: Throwable?
) : Exception(formatMessage(msg, code), exception) {

    constructor(
        errorType: ErrorType,
        code: Int,
        @StringRes msg: Int,
        exception: Throwable?
    ) : this(errorType, code, application.getString(msg), exception)

    init {
        cause?.printStackTrace()
    }

}


fun formatMessage(message: String?, code: Int): String? {
    return if (!message.isNullOrEmpty() && message.contains("%s")) {
        message.format(code)
    } else {
        message
    }
}

enum class ErrorType {
    PARSE, API, HTTP, OTHER
}

fun handleException(e: Throwable?): ApiException {

    return when (e) {
        is ApiException -> e

        is HttpException -> ApiException(
            ErrorType.HTTP,
            e.code(),
            R.string.net_error_http,
            e
        )

        is UnknownHostException,
        is ConnectException,
        is NoRouteToHostException,
        is SocketTimeoutException -> {
            ApiException(
                ErrorType.HTTP,
                0,
                R.string.net_error_disabled,
                e as Exception
            )
        }

        is ParseException,
        is JSONException,
        is JsonParseException -> {
            ApiException(
                ErrorType.PARSE,
                0,
                R.string.net_error_parse,
                e as Exception
            )
        }

        is Exception -> {
            ApiException(ErrorType.OTHER, 0, R.string.net_error_default, e)
        }

        else -> {
            ApiException(ErrorType.OTHER, 0, R.string.net_error_default, null)
        }
    }

}

