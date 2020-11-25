package com.example.imagedownloader

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ImageDownloaderService : IntentService("ImageDownloaderService") {

    override fun onCreate() {
        super.onCreate()
        Log.i("ImageDownloaderService", "Service created")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.i("ImageDownloaderService", "Service started")
        intent?.let {
            var link = intent.getStringExtra("IMAGE_LINK")
            link?.let {
                var bitmap = NetworkingUtils.downloadImage(link)
                if(bitmap != null) {
                    Log.i("ImageDownloader", "Image downloaded successfully")
                }

                val intent = Intent("com.example.imagedownloader.IMAGE_DOWNLOAD_COMPLETE")
                SharedData.imageBitmap = bitmap
                sendBroadcast(intent)
            }
        }
    }
}
