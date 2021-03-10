package pers.icebounded.mvvm.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by Andy
 * on 2021/2/25
 */
abstract class BaseMvvmActivity<V : ViewDataBinding, VM : BaseMvvmViewModel> : BaseActivity() {

    lateinit var binding: V
    lateinit var viewModel: VM

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
        viewModel = defaultViewModelProviderFactory.create(modelClass)
        viewModel.application = application
        lifecycle.addObserver(viewModel)
    }

    abstract fun getVariableId(): Int

    fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModel.viewModelScope.launch(context, start, block)
    }

}