package com.example.photosclient

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor


class DownloaderThread(var uiHandler:Handler, name: String) : HandlerThread(name) {
    companion object {
        val MESSAGE_DOWNLOAD = 0
    }
    lateinit var downloadRequestHandler:Handler
    var executorService = Executors.newFixedThreadPool(5)
    var requestMap:ConcurrentHashMap<PhotoHolder, String> = ConcurrentHashMap()

    fun enqueueDownload(photoHolder: PhotoHolder, url: String?) {
        if(url == null) {
            requestMap.remove(photoHolder)
        } else {
            requestMap.put(photoHolder, url)
            downloadRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, photoHolder).sendToTarget()
        }
    }
    override fun onLooperPrepared() {
        super.onLooperPrepared()
        downloadRequestHandler = object : Handler(looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if(msg.what == MESSAGE_DOWNLOAD) {
                    val photoHolder = msg.obj as PhotoHolder
                    executorService.execute(Runnable {
                        handleRequest(photoHolder)
                    })

                }
            }
        }
    }

    private fun handleRequest(holder: PhotoHolder) {
        try {
            val url: String = requestMap.get(holder) ?: return
            val urlObj = URL(url)
            urlObj.let {
                val connection = urlObj.openConnection()
                val inputStream = connection.getInputStream()
                val bitmap:Bitmap? = BitmapFactory.decodeStream(inputStream)
                bitmap?.let {
                    Log.i("DownloaderThread", "Bitmap created")
                    requestMap.remove(holder)
                    uiHandler.post{
                        holder.imageView.setImageBitmap(bitmap)
                    }
                }
            }


        } catch (io:IOException) {
            Log.e("DownloaderThread", "Error downloading image", io)
        } catch (url:MalformedURLException) {
            Log.e("DownloaderThread", "Malformed url", url )
        }
    }
}