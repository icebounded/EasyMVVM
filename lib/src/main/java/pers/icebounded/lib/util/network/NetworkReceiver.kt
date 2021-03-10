@file:Suppress("DEPRECATION")

package pers.icebounded.lib.util.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

/**
 * 网络状态接收广播
 * @author liming
 * @date 2020/05/21
 */
class NetworkReceiver(
    var context: Context?,
    val isConnection: (isconn: Boolean) -> Unit,
    val getNetType: (type: NetType) -> Unit
) : BroadcastReceiver() {

    fun registerReceiver() {
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context?.registerReceiver(this, filter)
    }

    fun destory() {
        context?.unregisterReceiver(this)
        context = null
    }

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context, intent: Intent?) {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var netType = getNetType(manager)
        isConnection(netType != NetType.NONE)
        getNetType(netType)
    }

}

