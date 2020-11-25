package com.example.imagedownloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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


class MainActivityPostRunnable : AppCompatActivity() {

    val link = "https://www.nasa.gov/sites/default/files/thumbnails/image/stsci-h-p2001a-m-2000x1500_0.png"
    lateinit var imageView:ImageView
    lateinit var handler:Handler
    var bitmap:Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)

        handler = Handler()
    }

    fun download(view: View) {
        val thread = Thread {
            bitmap = downloadImage(link)
            if (bitmap != null ) {
                Log.i("MainActivity", "Downloaded image successfully")

                handler.post {
                    imageView.setImageBitmap(bitmap)
                }

            } else {
                Log.i("MainActivity", "Unable to Download image")
            }
        }.start()
    }


    fun downloadImage(link:String): Bitmap? {
        var returnVal:Bitmap? = null
        try {
            val url = URL(link)
            val connection = url.openConnection()
            val inputStream = connection.getInputStream()
            returnVal = BitmapFactory.decodeStream(inputStream)
        }catch (url:MalformedURLException) {
            Log.i("MainActivity", "Malformed url ${url.message}")
            return null
        } catch (io:IOException) {
            Log.i("MainActivity", "IO Exception ${io.message}")
            return null
        }
        return returnVal
    }
}