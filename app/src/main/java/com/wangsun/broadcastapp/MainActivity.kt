package com.wangsun.broadcastapp

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val br = MyReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerBroadcast()
        initButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        /*Unregister receiver before closing app*/
        unregisterReceiver(br)
    }

    /*************************************************
     * From android '0'- oreo onwards you need to register
     * your broadcast receiver to receive broadcast
     ************************************************/
    private fun registerBroadcast() {
        val intentFilter = IntentFilter(Constants.Actions.RECEIVER_ACTION_NAME2)
        intentFilter.addAction(Constants.Actions.RECEIVER_ACTION_USER_ID2)
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        registerReceiver(br, intentFilter)
    }

    private fun initButton() {
        /*
        * Service
        * */
        id_start_service.setOnClickListener {
            startService()
        }

        id_stop_service.setOnClickListener {
            stopService()
        }




        id_send_broadcast_test.setOnClickListener {
            /*test broadcast*/
            val intent = Intent(Constants.Actions.BROADCAST_ACTION_NAME)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            sendBroadcast(intent)
        }

        id_send_broadcast_uid.setOnClickListener {
            /*uid broadcast*/
            val intent = Intent(Constants.Actions.BROADCAST_ACTION_USER_ID)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra(Constants.IntentKey.UID_KEY,"wangsun")
            sendBroadcast(intent)
        }

    }


    private fun startService() {
        if(!MyService.sIsRunning){
            val serviceIntent = Intent(this, MyService::class.java)
            serviceIntent.action = Constants.Actions.ACTION_START_SERVICE
            startService(serviceIntent)
        }
    }

    private fun stopService(){
        val serviceIntent = Intent(this, MyService::class.java)
        serviceIntent.action = Constants.Actions.ACTION_STOP_SERVICE
        startService(serviceIntent)
    }
}
