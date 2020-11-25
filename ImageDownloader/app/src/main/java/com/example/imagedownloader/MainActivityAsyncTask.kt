package com.example.imagedownloader

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

class ImageDownloaderTask(var imageView:WeakReference<ImageView>) : AsyncTask<String, Void, Bitmap? >(){

    override fun onPreExecute() {
        //UI initializations before async task starts the background thread
    }
    override fun doInBackground(vararg params: String?): Bitmap?
    {   val link = params[0]
        Log.i("ImageDownloaderTask", "$link")
        val bitmap = downloadImage(link!!)
        return bitmap
    }

    override fun onPostExecute(result: Bitmap?) {
        if(result != null) {
            Log.i("ImageDownloaderTask", "Image Downloaded successfully")
            imageView.get()?.setImageBitmap(result)
        } else {
            Log.i("ImageDownloaderTask", "Unable to download Image")
        }
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

class MainActivityAsyncTask : AppCompatActivity() {

    val link = "https://www.nasa.gov/sites/default/files/thumbnails/image/stsci-h-p2001a-m-2000x1500_0.png"
    lateinit var imageView:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
    }

    fun download(view: View) {
        val task = ImageDownloaderTask(WeakReference(imageView))
        task.execute(link)

    }


}