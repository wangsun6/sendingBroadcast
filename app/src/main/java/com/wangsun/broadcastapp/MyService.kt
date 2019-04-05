package com.wangsun.broadcastapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import org.jetbrains.anko.runOnUiThread

/**
 * Created by WANGSUN on 05-Apr-19.
 */
class MyService: Service() {
    private var builder: NotificationCompat.Builder? = null
    private lateinit var mContext: Context

    companion object {
        var sIsRunning = false
        val NOTIFICATION_ID = 100
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mContext = this



        val action = intent?.action
        when(action){
            Constants.Actions.ACTION_START_SERVICE-> {
                setNotification()
                sendMyBroadcast()

            }
            Constants.Actions.ACTION_STOP_SERVICE-> {
                removeNotification()
            }
        }
        return START_NOT_STICKY
    }



    private fun sendMyBroadcast() {
        sIsRunning = true

        val intent = Intent(Constants.Actions.BROADCAST_ACTION_NAME)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        sendBroadcast(intent)

        mContext.runOnUiThread {
            if(sIsRunning)
                Handler().postDelayed({ sendMyBroadcast() }, 10000)
        }

    }




    /*************************
     *   NOTIFICATION
     ****************************/
    private fun setNotification(){
        val notificationIntent = Intent(this, MainActivity::class.java)
        //notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)


        builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setTicker("this is ticker")
            .setContentTitle("Service Running")
            .setContentText("Service started...")
            .setSmallIcon(R.mipmap.ic_launcher)
            // .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOnlyAlertOnce(true) //important

        // Get an instance of the Notification manager
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "hahaha"
            // Create the channel for the notification
            val mChannel = NotificationChannel("CHANNEL_ID", name, NotificationManager.IMPORTANCE_LOW)
            mChannel.enableVibration(false)
            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel)
            // Channel ID
            builder?.setChannelId("CHANNEL_ID")
        }
        startForeground(NOTIFICATION_ID, builder?.build())
        updateNotification("Service started...")
    }

    private fun updateNotification(msg: String){
        builder?.let {
            it.setContentTitle("Service Running")
            it.setContentText(msg)
            startForeground(NOTIFICATION_ID, it.build())
        }
    }

    private fun removeNotification(){
        stopForeground(true)
        stopSelf()
        sIsRunning = false
    }
}