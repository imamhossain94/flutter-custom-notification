package com.newagedevs.flutter_custom_notification

import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity: FlutterActivity() {
    //Create a channel named same as "flutter.native/helper" in the methodChannel
    //Declared in main.dart file line number- 38
    private val channel = "flutter.native/helper"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(FlutterEngine(this))

        //This is a communicating method for calling named channel  = "flutter.native/helper" asynchronously
        MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, channel).setMethodCallHandler { call, result ->

            // check Invoked method name that was requested from main.dart file and response to it.
            if (call.method.equals("startNativeActivity")) {
                val greetings = startNativeActivity()
                result.success(greetings)
            }

        }
    }

    private fun startNativeActivity(): String {
        //starting a native activity create in android studio using xml & kotlin.
        startActivity(Intent(this, NativeActivity::class.java))
        return "Success"
    }

}