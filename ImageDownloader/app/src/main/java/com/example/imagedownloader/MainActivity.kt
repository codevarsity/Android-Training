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

class ImageHandler(var bitmap:Bitmap?, var imageView:WeakReference<ImageView>) : Handler() {
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        Log.i("MainActivity", "handleMessage called in ${Thread.currentThread().name}")
        Log.i("MainActivity", "Message from secondary thread with msg.what = ${msg.what}")
        if(bitmap != null) {
            imageView.get()?.setImageBitmap(bitmap)
        }
    }
}

class MainActivity : AppCompatActivity() {

    val link = "https://www.nasa.gov/sites/default/files/thumbnails/image/stsci-h-p2001a-m-2000x1500_0.png"
    lateinit var imageView:ImageView
    lateinit var handler:ImageHandler
    var bitmap:Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(BlankFragment(), "Blank").commit()
        imageView = findViewById(R.id.imageView)

        handler = ImageHandler(bitmap, WeakReference(imageView))
    }

    fun download(view: View) {
        val thread = Thread {
            bitmap = downloadImage(link)
            if (bitmap != null ) {
                Log.i("MainActivity", "Downloaded image successfully")
                val message = Message.obtain()
                message.apply { what = 101 }
                handler.bitmap = bitmap
                handler.sendMessage(message)

                Log.i("MainActivity",
                    "handler.sendMessage called by\n ${Thread.currentThread().name}")

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