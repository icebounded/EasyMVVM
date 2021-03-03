package com.github.icebounded.mvvm.demo

import android.content.Context
import android.content.Intent
import android.view.View
import com.github.icebounded.mvvm.base.BaseMvvmActivity
import com.github.icebounded.mvvm.demo.databinding.AppActivityMain2Binding
import com.github.icebounded.net.retorfit.CookieStore
import com.github.icebounded.net.retorfit.cookie.CookieSerializer
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cookie

/**
 * Created by Andy
 * on 2021/2/25
 */
fun starter(context: Context) {
    context.startActivity(Intent(context, MainActivity2::class.java))
}

data class DAA(val name : String, val age : Long) : java.io.Serializable

class MainActivity2 : BaseMvvmActivity<AppActivityMain2Binding, MainViewModel>() {

    override fun getLayoutId(): Int = R.layout.app_activity_main2

    @InternalSerializationApi
    override fun bindView(view: View) {

        val cookie = Cookie.Builder().apply {
            name("cookie_name")
            value("cookie_value")
            expiresAt(System.currentTimeMillis())
            if (true) hostOnlyDomain("www.baidu.com") else domain("www.baidu.com")
            path("/")
            if (true) secure()
            if (true) httpOnly()
        }.build()


        Json.decodeFromString<Cookie>(CookieSerializer(), Json.encodeToString(CookieSerializer(), cookie))

        val cookie1 = Cookie.Builder().apply {
            name("cookie_name1")
            value("cookie_value1")
            expiresAt(System.currentTimeMillis())
            if (true) hostOnlyDomain("www.baidu.com") else domain("www.baidu.com")
            path("/")
            if (true) secure()
            if (true) httpOnly()
        }.build()

        val cookieStore = CookieStore(application, "aaa")

        cookieStore.add("www.baidu.com", cookie)
        cookieStore.add("www.baidu.com", cookie1)

        val cookieStore1 = CookieStore(application, "aaa")

    }

    override fun getVariableId(): Int = 2

    override fun isSaveStateViewModel(): Boolean = false

}