package com.github.icebounded.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Created by liuyanxi
 * on 2021/2/25
 */
abstract class BaseFragment : Fragment() {

    protected var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getLayoutId(), container, false)
        bindView(rootView!!)
        return rootView
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun bindView(view: View)

}