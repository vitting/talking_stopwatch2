package com.example.talking_stopwatch2


import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

internal class NotificationAction(private val mContext: Context) {
    private var mNotificationManager: NotificationManagerCompat? = null

    init {

        createNotificationChannel()
        if (mNotificationManager == null) {
            mNotificationManager = NotificationManagerCompat.from(mContext)
        }
    }

    fun showNotification(title: String?, body: String?, actionButtonToShow: String?, button1Text: String?, button2Text: String?, button3Text: String?) {
        val builder: NotificationCompat.Builder
        val showPlay = actionButtonToShow != null && actionButtonToShow == "play"
        builder = buildNotification(title, body, button1Text, button2Text, button3Text, showPlay)
        mNotificationManager!!.notify(0, builder.build())
    }

    fun cancelNotification() {
        mNotificationManager!!.cancel(0)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = mContext.getString(R.string.channel_name)
            val description = mContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = mContext.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(title: String?, body: String?, buttonText: String?, button2Text: String?, button3Text: String?, showPlayButton: Boolean): NotificationCompat.Builder {
        return NotificationCompat.Builder(mContext, CHANNEL)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.notification_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(null)
                .setVibrate(null)
                .setShowWhen(false)
                .setOnlyAlertOnce(true)
                .setContentIntent(buttonShowActivity())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(if (showPlayButton) R.drawable.ic_play else R.drawable.ic_pause, buttonText, buttonPlayPause(showPlayButton))
                .addAction(R.drawable.ic_reset, button2Text, buttonReset())
        //.addAction(R.drawable.ic_exit, button3Text, buttonExit());
    }

    // On Tap show activity
    private fun buttonShowActivity(): PendingIntent {
        val intent = Intent(mContext, mContext.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
        return PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    // Action button play/pause
    private fun buttonPlayPause(showPlayButton: Boolean): PendingIntent {
        val playPauseIntent = Intent(mContext, NotificationActionBroardcastReceiver::class.java)
        playPauseIntent
                .setAction("playPausePress")
                .putExtra("PLAYPAUSEBUTTONSTATUS", if (showPlayButton) "action_play" else "action_pause")

        val playPausePendingIntent = PendingIntent.getBroadcast(mContext, 0, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            playPauseIntent.putExtra(EXTRA_NOTIFICATION_ID, 0)
        }

        return playPausePendingIntent
    }

    // Action button reset
    private fun buttonReset(): PendingIntent {
        val resetIntent = Intent(mContext, NotificationActionBroardcastReceiver::class.java)
        resetIntent
                .setAction("resetPress")
                .putExtra("RESETBUTTONSTATUS", "action_reset")

        val resetPendingIntent = PendingIntent.getBroadcast(mContext, 0, resetIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resetIntent.putExtra(EXTRA_NOTIFICATION_ID, 0)
        }

        return resetPendingIntent
    }

    // Action button reset
    private fun buttonExit(): PendingIntent {
        val exitIntent = Intent(mContext, NotificationActionBroardcastReceiver::class.java)
        exitIntent
                .setAction("exitPress")
                .putExtra("EXITBUTTONSTATUS", "action_exit")

        val exitPendingIntent = PendingIntent.getBroadcast(mContext, 0, exitIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            exitIntent.putExtra(EXTRA_NOTIFICATION_ID, 0)
        }

        return exitPendingIntent
    }

    companion object {
        const val CHANNEL = "talking.stopwatch.dk/notification"
        const val EVENTCHANNEL = "talking.stopwatch.dk/stream"
    }
}
