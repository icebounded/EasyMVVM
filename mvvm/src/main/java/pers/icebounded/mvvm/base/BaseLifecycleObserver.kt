package pers.icebounded.mvvm.base

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import pers.icebounded.lib.ext.logd
import pers.icebounded.lib.ext.simpleName

/**
 * Created by Andy
 * on 2021/2/25
 */
interface BaseLifecycleObserver : LifecycleObserver {

    var lifecycleState: Lifecycle.State
    var paused: Boolean

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(owner: LifecycleOwner) {
        paused = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(owner: LifecycleOwner) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onDestroy(owner: LifecycleOwner) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        if (lifecycleState != Lifecycle.State.DESTROYED) {
            lifecycleState = owner.lifecycle.currentState
        }

        // 检查Activity是否已经finish
        if(lifecycleState != Lifecycle.State.DESTROYED) {
            var activityFinish = (owner is Activity && owner.isFinishing)
                    || (owner is Fragment && owner.activity?.isFinishing ?: true)
            if(activityFinish) {
                onActivityFinish(owner)
            }
        }

        (event.name + "-" + paused).logd(simpleName)
    }

    fun onActivityFinish(owner: LifecycleOwner) {
        lifecycleState = Lifecycle.State.DESTROYED
        "onActivityFinish".logd(simpleName)
    }

}