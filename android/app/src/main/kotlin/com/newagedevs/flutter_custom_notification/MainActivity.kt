package com.newagedevs.flutter_custom_notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
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
            when {
                call.method.equals("startNativeActivity") -> {

                    val greetings = startNativeActivity()
                    result.success(greetings)

                }
                call.method.equals("startNativeActivityWithArgs") -> {

                    val greetings = startNativeActivity(call.arguments as HashMap<*, *>?)
                    result.success(greetings)

                }
                call.method.equals("showNotificationFromNative") -> {

                    val title = call.argument<String>("title")?:"Empty"
                    val message = call.argument<String>("message")?:"Empty"
                    val greetings = showSimpleNotification(title, message)
                    result.success(greetings)

                }
                call.method.equals("showCustomNotificationFromNative") -> {

                    val args = call.arguments as HashMap<*, *>?
                    val greetings = showCustomNotification(args)
                    result.success(greetings)

                }
            }

        }
    }

    private fun startNativeActivity(): String {
        //starting a native activity create in android studio using xml & kotlin.
        startActivity(Intent(this, NativeActivity::class.java))
        return "Success"
    }

    private fun startNativeActivity(args: HashMap<*, *>?): String {
        //starting a native activity create in android studio using xml & kotlin.
        //here we put extra argument with the intent
        startActivity(Intent(this, NativeActivity::class.java).putExtra("args", args))
        return "Success"
    }

    private fun showSimpleNotification(title:String, message:String): String {

        //Code copied from https://www.geeksforgeeks.org/notifications-in-kotlin/
        //visit this link to learn about android notification.
        //Removed unnecessary code
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        lateinit var notificationChannel: NotificationChannel
        lateinit var builder: Notification.Builder
        val channelId = "i.apps.notifications"
        val description = "Simple Notification"

        val intent = Intent(this, NativeActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this, channelId)
                    .setContentTitle(title)
                    .setContentText(message)
                    //setContent Only works on api less then 16
                    //.setContent(contentView)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
        } else {

            //Builder class & setContent is deprecated. I will Fix it later.
            builder = Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(message)
                    //setContent Only works on api less then 16
                    //.setContent(contentView)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())

        return "Success"
    }


    private fun showCustomNotification(args: HashMap<*, *>?):String {


        val notificationLayout = RemoteViews(packageName, R.layout.notification_small)
        //val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_large)


        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        lateinit var notificationChannel: NotificationChannel
        lateinit var builder: Notification.Builder
        val channelId = "i.apps.notifications"
        val description = "Custom Notification"

        val intent = Intent(this, NativeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this, channelId)
                    .setCustomContentView(notificationLayout)
                    //.setCustomBigContentView(notificationLayoutExpanded)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
        } else {

            //Builder class & setContent is deprecated. I will Fix it later.
            builder = Notification.Builder(this)
                    .setContent(notificationLayout)
                    //.setCustomBigContentView(notificationLayoutExpanded)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())

        return "Success"

    }

}