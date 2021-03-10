package pers.icebounded.mvvm.demo

import android.os.Bundle
import android.view.View
import pers.icebounded.mvvm.base.BaseMvvmActivity
import pers.icebounded.mvvm.demo.databinding.AppActivityMainBinding
import pers.icebounded.mvvm.demo.paging.PagingTestActivity

/**
 * Created by Andy
 * on 2021/2/25
 */
class MainActivity : BaseMvvmActivity<AppActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.data = viewModel
    }

    fun onClick(view: View) {
        PagingTestActivity.start(this)
    }

    override fun getLayoutId() = R.layout.app_activity_main


    override fun bindView(view: View) {

    }

    override fun getVariableId() = 2

}