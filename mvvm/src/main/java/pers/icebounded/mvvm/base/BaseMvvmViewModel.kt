package pers.icebounded.mvvm.base

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import pers.icebounded.lib.statelayout.StateLayout
import pers.icebounded.mvvm.adapter.PageState

/**
 * Created by Andy
 * on 2021/2/25
 */
abstract class BaseMvvmViewModel(val savedStateHandle: SavedStateHandle? = null) : ViewModel(), BaseLifecycleObserver {

    val pageState = MutableLiveData(PageState(StateLayout.Status.LOADING))

    override var lifecycleState: Lifecycle.State = Lifecycle.State.INITIALIZED

    override var paused: Boolean = false

    var application: Application? = null

    open fun showLoading() {
        pageState.postValue(PageState(StateLayout.Status.LOADING))
    }

    open fun showEmpty() {
        pageState.postValue(PageState(StateLayout.Status.EMPTY))
    }

    open fun showContent() {
        pageState.postValue(PageState(StateLayout.Status.CONTENT))
    }

    open fun showError(message: String?) {
        pageState.postValue(PageState(StateLayout.Status.ERROR, message))
    }

}

