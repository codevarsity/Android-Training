package com.example.imagedownloader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ImageView
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivityService : AppCompatActivity() {

    val link = "https://www.nasa.gov/sites/default/files/thumbnails/image/stsci-h-p2001a-m-2000x1500_0.png"
    lateinit var imageView:ImageView

    var receiver:BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i("MainActivityService", "Broadcast Receiver executed")
            if(SharedData.imageBitmap != null) {
                imageView.setImageBitmap(SharedData.imageBitmap)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("com.example.imagedownloader.IMAGE_DOWNLOAD_COMPLETE")
        registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
    }

    fun download(view: View) {
        val intent = Intent(this, ImageDownloaderService::class.java)
        intent.putExtra("IMAGE_LINK", link)
        startService(intent)
    }
}