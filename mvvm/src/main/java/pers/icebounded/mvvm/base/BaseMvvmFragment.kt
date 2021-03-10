package pers.icebounded.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.lang.reflect.ParameterizedType

/**
 * Created by Andy
 * on 2021/2/25
 */
abstract class BaseMvvmFragment<V : ViewDataBinding, VM : BaseMvvmViewModel> : BaseFragment() {

    lateinit var binding: V
    lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createViewModel()
        createDataBinding(inflater, container)
        bindView(rootView!!)
        return rootView
    }

    private fun createDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        rootView = binding.root
        binding.lifecycleOwner = this
        binding.setVariable(getVariableId(), viewModel)
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
        viewModel.application = requireActivity().application
        lifecycle.addObserver(viewModel)
    }

    abstract fun getVariableId(): Int

}