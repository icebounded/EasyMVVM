package pers.icebounded.net.response

import org.json.JSONObject

/**
 * Created by Andy
 * on 2021/3/10
 */
abstract class ApiData {
    abstract fun parse(dataJson: JSONObject?, responseJson: JSONObject)
}