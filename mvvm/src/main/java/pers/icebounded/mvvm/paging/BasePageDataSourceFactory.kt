package pers.icebounded.mvvm.paging

import androidx.paging.DataSource
import pers.icebounded.net.response.ApiListData
import pers.icebounded.net.response.ApiResponse

/**
 * Created by Andy
 * on 2021/3/9
 */
class BasePageDataSourceFactory<Value, ListData: ApiListData<Value>>(
    val viewModel: BasePageViewModel<Value, ListData>,
    val requestData: suspend (pageNum: Int, rowNum: Int) -> ApiResponse<ListData>
) :
    DataSource.Factory<Int, Value>() {

    var sourceLiveData: BasePageDataSource<Value, ListData>? = null

    override fun create(): DataSource<Int, Value> {
        sourceLiveData = BasePageDataSource(viewModel, requestData)
        return sourceLiveData!!
    }

}