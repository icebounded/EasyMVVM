package com.github.icebounded.mvvm.demo

import android.os.Bundle
import android.view.View
import com.github.icebounded.mvvm.base.BaseMvvmActivity
import com.github.icebounded.mvvm.demo.databinding.AppActivityMainBinding

/**
 * Created by liuyanxi
 * on 2021/2/25
 */
class MainActivity : BaseMvvmActivity<AppActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.data = viewModel
    }

    fun onClick(view: View) {
        starter(this)
    }

    override fun getLayoutId() = R.layout.app_activity_main


    override fun bindView(view: View) {

    }

    override fun getVariableId() = 2

    override fun isSaveStateViewModel() = true

}