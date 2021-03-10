package pers.icebounded.mvvm.demo.service

import pers.icebounded.mvvm.demo.paging.ArtListData
import pers.icebounded.net.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Andy
 * on 2021/3/9
 */
interface DemoService {
    @GET("object")
    suspend fun getObjectList(@Query("page") page: Int, @Query("size") size: Int): ApiResponse<ArtListData>
}