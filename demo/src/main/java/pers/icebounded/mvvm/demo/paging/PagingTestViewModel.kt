package pers.icebounded.mvvm.demo.paging

import pers.icebounded.mvvm.demo.service.DemoService
import pers.icebounded.mvvm.paging.BasePageViewModel
import pers.icebounded.net.response.ApiResponse
import pers.icebounded.net.retorfit.createHttpService
import pers.icebounded.net.retorfit.requestHttp

/**
 * Created by Andy
 * on 2021/3/9
 */
class PagingTestViewModel : BasePageViewModel<Art, ArtListData>() {

    val service by lazy {
        createHttpService<DemoService>()
    }

    override suspend fun requestData(pageNum: Int, rowNum: Int): ApiResponse<ArtListData> {
        return requestHttp { service.getObjectList(pageNum, rowNum) }
    }

}