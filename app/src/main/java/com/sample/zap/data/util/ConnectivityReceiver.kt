package com.sample.zap.data.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
            if (NetworkUtil.isInternetAvailable(context)) {
                // Internet is connected
                Toast.makeText(context, "Internet connected", Toast.LENGTH_SHORT).show()
            } else {
                // Internet is disconnected
                Toast.makeText(context, "Internet disconnected", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
