package com.example.applicationessentials

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class FileDownloaderService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i("FileDownloaderService", "Created Service")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("FileDownloaderService", "Started Service")
        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
