package com.example.talking_stopwatch2

import android.os.Bundle

import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterActivity(), MethodCallHandler, StreamHandler {
    private var mNotificationAction: NotificationAction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        // We use this because this class implements MethodCallHander
        val channel = MethodChannel(flutterView, NotificationAction.CHANNEL)
        // We use this because this class implements StreamHandler;
        channel.setMethodCallHandler(this)

        val eventChannel = EventChannel(flutterView, NotificationAction.EVENTCHANNEL)
        eventChannel.setStreamHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "showNotification" -> {
                try {
                    mNotificationAction!!.showNotification(
                            call.argument<String>("title"),
                            call.argument<String>("body"),
                            call.argument<String>("actionButtonToShow"),
                            call.argument<String>("buttonText"),
                            call.argument<String>("button2Text"),
                            call.argument<String>("button3Text")
                    )
                } catch (e: NullPointerException) {
                    result.error("Parameter error", null, e)
                }

                result.success(true)
            }
            "cancelNotification" -> {
                mNotificationAction!!.cancelNotification()
                result.success(true)
            }
            "initializeNotification" -> {
                mNotificationAction = NotificationAction(this)
                result.success(true)
            }
            else -> result.success(false)
        }
    }

    override fun onListen(o: Any?, eventSink: EventSink) {
        if (mEventSink == null) {
            mEventSink = eventSink
        }
    }

    override fun onCancel(o: Any?) {
        mEventSink = null
    }

    companion object {
        var mEventSink: EventSink? = null
    }
}
