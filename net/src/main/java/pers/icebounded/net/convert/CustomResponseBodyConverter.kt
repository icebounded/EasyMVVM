package pers.icebounded.net.convert

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.json.JSONObject
import pers.icebounded.net.response.ApiResponse
import pers.icebounded.net.secret.ApiAESCipher
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * Created by Andy
 * on 2021/3/4
 */
class CustomResponseBodyConverter(private val type: Type) :
    Converter<ResponseBody, ApiResponse<*>> {

    private val apiCipher = ApiAESCipher("123")

    val gson: Gson by lazy { GsonBuilder().serializeNulls().create() }

    override fun convert(value: ResponseBody): ApiResponse<*> {
        val text = value.string()
        val json = runCatching { JSONObject(text) }.getOrElse { JSONObject(apiCipher.decrypt(text)) }
        val response: ApiResponse<*> = gson.fromJson("{\"data\":{}}", type)
        response.parse(json)
        return response
    }
}