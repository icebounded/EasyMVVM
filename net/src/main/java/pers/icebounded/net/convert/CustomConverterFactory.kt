package pers.icebounded.net.convert

import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import pers.icebounded.net.response.ApiResponse
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by Andy
 * on 2021/3/4
 */
class CustomConverterFactory() : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val c = TypeToken.get(type).rawType
        if (ApiResponse::class.java.isAssignableFrom(c)) {
            return CustomResponseBodyConverter(type)
        }
        return null
    }

}