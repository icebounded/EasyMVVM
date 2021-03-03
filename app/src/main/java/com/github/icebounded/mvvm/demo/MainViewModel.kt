package com.github.icebounded.mvvm.demo

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.icebounded.base.ext.simpleName
import com.github.icebounded.base.extensions.logd
import com.github.icebounded.mvvm.base.BaseMvvmViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

/**
 * Created by Andy
 * on 2021/2/25
 */
class MainViewModel(savedStateHandle: SavedStateHandle) : BaseMvvmViewModel(savedStateHandle) {

    var test: Boolean? = null

    fun increaseA(count: Int) {
        getScore1().value!!.number = (getScore1().value!!.number ?: 0) + count
        getScore1().value = getScore1().value
        logd(simpleName, "increaseA: " + (test?.toString()))
        viewModelScope.launch {

        }
    }

    fun increaseB(count: Int) {
        getScore2().value!!.number = (getScore2().value!!.number ?: 0) + count
        getScore2().value = getScore2().value
    }

    fun getScore1(): MutableLiveData<NumberTest> {
        return savedSate!!.getLiveData("1", NumberTest())
    }

    fun getScore2(): MutableLiveData<NumberTest> {
        return savedSate!!.getLiveData("2", NumberTest())
    }

}

@Parcelize
class NumberTest: Parcelable {
    var number: Int? = null
}