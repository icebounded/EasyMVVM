package pers.icebounded.mvvm.demo.paging

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import pers.icebounded.net.response.ApiListData

/**
 * Created by Andy
 * on 2021/3/9
 */
class ArtListData : ApiListData<Art>() {

    val json = Json {
        this.ignoreUnknownKeys = true
        this.coerceInputValues = true
    }

    override fun parseDataList(dataJson: JSONObject?, responseJson: JSONObject) {
        var optJSONArray = responseJson.optJSONArray("records")
        optJSONArray?.let {
            for (i in 0 until optJSONArray.length()) {
                dataList.add(json.decodeFromString(optJSONArray.getString(i)))
            }
        }
    }
}