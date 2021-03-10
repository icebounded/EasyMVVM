package pers.icebounded.mvvm.demo

import androidx.multidex.MultiDexApplication
import pers.icebounded.lib.statelayout.StateConfig

/**
 * Created by Andy
 * on 2021/3/8
 */
class AppApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        StateConfig.apply {
            loadingLayout = R.layout.app_layout_loading // 配置全局的加载中布局
            errorLayout = R.layout.app_layout_error // 配置全局的错误布局
            emptyLayout = R.layout.app_layout_empty // 配置全局的空布局

//            setRetryIds(R.id.msg) // 全局的重试Id

            onLoading {

            }

            onEmpty {

            }

            onError {

            }
        }
    }
}