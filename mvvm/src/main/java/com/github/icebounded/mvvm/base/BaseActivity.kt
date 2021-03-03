package com.github.icebounded.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Keep
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Andy
 * on 2021/2/25
 */



abstract class BaseActivity : AppCompatActivity() {

    protected var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configView(savedInstanceState)
    }

    open fun configView(savedInstanceState: Bundle?) {
        rootView = LayoutInflater.from(this).inflate(getLayoutId(), null)
        setContentView(rootView)
        bindView(rootView!!)
    }

    @Keep
    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun bindView(view: View)

}