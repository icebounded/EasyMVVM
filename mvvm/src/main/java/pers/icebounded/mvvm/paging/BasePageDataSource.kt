package pers.icebounded.mvvm.paging

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.viewModelScope
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pers.icebounded.net.response.ApiListData
import pers.icebounded.net.response.ApiResponse
import java.util.*

/**
 * Created by Andy
 * on 2021/3/9
 */
class BasePageDataSource<Value, ListData : ApiListData<Value>>(
    val viewModel: BasePageViewModel<Value, ListData>,
    val requestData: suspend (pageNum: Int, rowNum: Int) -> ApiResponse<ListData>
) :
    PageKeyedDataSource<Int, Value>() {

    @SuppressLint("RestrictedApi")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Value>) {
        runBlocking {
            val result = requestData.invoke(1, params.requestedLoadSize)
            result.success {
                callback.onResult(this.data.dataList, 0, 2)
                viewModel.showContent()
            }
            result.error {
                viewModel.showError(message)
                callback.onResult(arrayListOf(), 0, 2)
            }
            fixLoadMore(result.data, params.requestedLoadSize)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {
        callback.onResult(Collections.emptyList(), 0);
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Value>) {
        runBlocking {
            val result = requestData.invoke(params.key, params.requestedLoadSize)
            result.success {
                callback.onResult(data.dataList, params.key + 1)
                viewModel.viewModelScope.launch(Dispatchers.Main) {
                    viewModel.setHasMore(data.hasMore())
                }
                viewModel.showContent()
            }
            result.error {
                viewModel.showError(message)
                callback.onResult(arrayListOf(), params.key)
            }
            fixLoadMore(result.data, params.requestedLoadSize)
        }
    }

    @SuppressLint("RestrictedApi")
    fun fixLoadMore(data: ListData?, requestedLoadSize: Int) {
        if (data == null || (data.hasMore() && data.dataList.size < requestedLoadSize)) {
            ArchTaskExecutor.getMainThreadExecutor().execute {
                viewModel.setHasMore(true)
            }
        }
    }

}