package pers.icebounded.mvvm.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import pers.icebounded.mvvm.base.BaseMvvmViewModel
import pers.icebounded.net.response.ApiListData
import pers.icebounded.net.response.ApiResponse

/**
 * Created by Andy
 * on 2021/3/9
 */
abstract class BasePageViewModel<Value, ListData : ApiListData<Value>>(savedStateHandle: SavedStateHandle? = null) :
    BaseMvvmViewModel(savedStateHandle) {

    var dataFactory = BasePageDataSourceFactory(this, ::requestData)

    var mDataList: LiveData<PagedList<Value>>

    init {
        mDataList = LivePagedListBuilder(dataFactory, pagedListConfig().build()).build()
    }

    fun refresh() {
        dataFactory.sourceLiveData?.invalidate()
    }

    fun setHasMore(hasMore: Boolean) {
        kotlin.runCatching {
            var javaClass = mDataList.value!!.javaClass
            var declaredField = javaClass.getDeclaredField("mAppendWorkerState")
            declaredField.isAccessible = true
            declaredField.set(mDataList.value!!, if (hasMore) 0 else 2)
        }
    }

    abstract suspend fun requestData(pageNum: Int, rowNum: Int): ApiResponse<ListData>

    open fun pagedListConfig(): PagedList.Config.Builder {
        return PagedList.Config.Builder().apply {
            setPageSize(10)
            setEnablePlaceholders(false)
            setPrefetchDistance(3)
            setInitialLoadSizeHint(10)
        }
    }
}