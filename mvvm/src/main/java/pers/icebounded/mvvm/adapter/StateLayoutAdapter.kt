package pers.icebounded.mvvm.adapter

import androidx.databinding.BindingAdapter
import pers.icebounded.lib.statelayout.StateLayout

/**
 * Created by Andy
 * on 2021/3/8
 */
object StateLayoutAdapter {
    @kotlin.jvm.JvmStatic
    @BindingAdapter(value = ["page_state"], requireAll = false)
    fun setLayoutState(view: StateLayout, page_state: PageState) {
        when (page_state.status) {
            StateLayout.Status.LOADING -> view.showLoading()
            StateLayout.Status.CONTENT -> view.showContent()
            StateLayout.Status.EMPTY -> view.showEmpty()
            StateLayout.Status.ERROR -> {
                if (view.status != StateLayout.Status.CONTENT) {
                    view.showError(page_state.message)
                }
            }
        }
    }
}

data class PageState(val status: StateLayout.Status = StateLayout.Status.LOADING, var message: String? = null) {

}