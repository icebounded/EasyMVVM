package pers.icebounded.net.response

import org.json.JSONObject

/**
 * Created by Andy
 * on 2021/3/10
 */
abstract class ApiListData<Value> : ApiData() {

    var pageNum = 0
    var pageTotal: Int = 0
    var rowsNum: Int = 0
    var rowsTotal: Int = 0

    val dataList = arrayListOf<Value>()

    override fun parse(dataJson: JSONObject?, responseJson: JSONObject) {
        responseJson?.optJSONObject("info")?.apply {
            pageNum = optInt("page")
            pageTotal = optInt("pages")
            rowsNum = optInt("totalrecordsperquery")
            rowsTotal = optInt("totalrecords")
        }
        parseDataList(dataJson, responseJson)
    }

    abstract fun parseDataList(dataJson: JSONObject?, responseJson: JSONObject)

    open fun hasMore() = pageTotal > pageNum

}