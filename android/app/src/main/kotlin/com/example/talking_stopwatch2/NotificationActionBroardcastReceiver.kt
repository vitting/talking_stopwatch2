package com.example.talking_stopwatch2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationActionBroardcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "playPausePress" -> if (MainActivity.mEventSink != null) {
                val currentButtonAction = intent.getStringExtra("PLAYPAUSEBUTTONSTATUS")

                MainActivity.mEventSink!!.success(currentButtonAction)
            }
            "resetPress" -> if (MainActivity.mEventSink != null) {
                val currentButtonAction = intent.getStringExtra("RESETBUTTONSTATUS")

                MainActivity.mEventSink!!.success(currentButtonAction)
            }
            "exitPress" -> if (MainActivity.mEventSink != null) {
                val currentButtonAction = intent.getStringExtra("EXITBUTTONSTATUS")

                MainActivity.mEventSink!!.success(currentButtonAction)
            }
        }
    }
}
