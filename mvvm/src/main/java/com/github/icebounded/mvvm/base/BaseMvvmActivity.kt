package com.github.icebounded.mvvm.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * Created by Andy
 * on 2021/2/25
 */
abstract class BaseMvvmActivity<V : ViewDataBinding, VM : BaseMvvmViewModel> : BaseActivity() {

    protected lateinit var binding: V
    protected lateinit var viewModel: VM

    override fun configView(savedInstanceState: Bundle?) {
        createViewModel()
        createDataBinding()
        bindView(rootView!!)
    }

    private fun createDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setVariable(getVariableId(), viewModel)
        binding.lifecycleOwner = this
        rootView = binding.root
    }

    private fun createViewModel() {

        val modelClass: Class<VM>
        val type = javaClass.genericSuperclass

        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<VM>
        } else {
            //如果没有指定泛型参数，则默认使用BaseMvvmViewModle
            BaseMvvmViewModel::class.java as Class<VM>
        }

        viewModel = if (isSaveStateViewModel()) {
            ViewModelProvider(this, SavedStateViewModelFactory(application, this)).get(modelClass)
        } else {
            ViewModelProvider(this).get(modelClass)
        }
        viewModel.application = application
        lifecycle.addObserver(viewModel)
    }

    abstract fun getVariableId(): Int

    abstract fun isSaveStateViewModel(): Boolean

}