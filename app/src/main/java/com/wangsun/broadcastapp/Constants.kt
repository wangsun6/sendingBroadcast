package com.wangsun.broadcastapp

/**
 * Created by WANGSUN on 05-Apr-19.
 */
class Constants {
    object Actions{
        /*start activity with intent*/
        val ACTION_START_ACTIVITY = "com.kontiki.action.START_ACTIVITY"

        /*broadcast*/
        val BROADCAST_ACTION_NAME = "com.kontiki.action.NAME"
        val BROADCAST_ACTION_USER_ID = "com.kontiki.action.USER_ID"

        /*receiver*/
        val RECEIVER_ACTION_NAME2 = "com.kontiki.action.NAME2"
        val RECEIVER_ACTION_USER_ID2 = "com.kontiki.action.USER_ID2"

        /*service*/
        val ACTION_START_SERVICE = "com.kontiki.action.START_SERVICE"
        val ACTION_STOP_SERVICE = "com.kontiki.action.STOP_SERVICE"

    }

    object IntentKey{
        /*keys*/
        val UID_KEY = "uidKey"
    }
}