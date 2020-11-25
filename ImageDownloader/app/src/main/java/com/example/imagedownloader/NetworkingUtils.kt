package com.example.imagedownloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

object NetworkingUtils {
    fun downloadImage(link:String): Bitmap? {
        var returnVal: Bitmap? = null
        try {
            val url = URL(link)
            val connection = url.openConnection()
            val inputStream = connection.getInputStream()
            returnVal = BitmapFactory.decodeStream(inputStream)
        }catch (url: MalformedURLException) {
            Log.i("MainActivity", "Malformed url ${url.message}")
            return null
        } catch (io: IOException) {
            Log.i("MainActivity", "IO Exception ${io.message}")
            return null
        }
        return returnVal
    }
}