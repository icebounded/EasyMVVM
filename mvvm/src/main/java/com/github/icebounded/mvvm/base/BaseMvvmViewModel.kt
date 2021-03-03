package com.github.icebounded.mvvm.base

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * Created by liuyanxi
 * on 2021/2/25
 */
abstract class BaseMvvmViewModel() : ViewModel(), BaseLifecycleObserver {

    override var lifecycleState: Lifecycle.State = Lifecycle.State.INITIALIZED
    override var paused: Boolean = false

    protected var savedSate: SavedStateHandle? = null

    var application: Application? = null

    constructor(savedStateHandle: SavedStateHandle) : this() {
        savedSate = savedStateHandle
    }

}