package com.example.reproductormusica

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MusicNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, MusicService::class.java)
        serviceIntent.action = intent?.action
        context?.startService(serviceIntent)
    }
}
