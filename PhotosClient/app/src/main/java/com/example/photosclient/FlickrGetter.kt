package com.hellotech7.imagesearchapp

import android.net.Uri
import android.util.Log
import com.example.photosclient.PhotoItem
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
//  "flickr.photos.search"
//  "flickr.photos.getRecent"

object FlickrAPI {
    val SEARCH = "flickr.photos.search"
    var RECENT = "flickr.photos.getRecent"

    fun getFlickrUriBuilder(method:String):Uri.Builder {
        var uriBuilder = Uri.parse("https://api.flickr.com/services/rest/")
            .buildUpon()
            .appendQueryParameter("method", method)
            .appendQueryParameter("api_key", "f2e92bdade8c1afb7dc1005c2272d89d")
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("extras", "url_s")
        return uriBuilder
    }

}

object FlickrGetter {
    fun getJSONString(query:String):String? {
        var finalURLString = FlickrAPI.getFlickrUriBuilder(FlickrAPI.SEARCH).appendQueryParameter("text", query).build().toString()
        Log.i("FlickrGetter", "$finalURLString")
        var response = getUrlBytes(finalURLString)
        return if(response != null) { String(response) } else { null }

    }

    fun getJSONString():String? {
        var finalURLString = FlickrAPI.getFlickrUriBuilder(FlickrAPI.RECENT).build().toString()
        Log.i("FlickrGetter", "$finalURLString")
        var response = getUrlBytes(finalURLString)
        return if(response != null) { String(response) } else { null }
    }

    fun getFlickrItems():List<PhotoItem>? {
        return getJSONString()?.let {
            var items = ArrayList<PhotoItem>()
            val jsonBody = JSONObject(it)
            val photosJsonObject = jsonBody.getJSONObject("photos")
            val photoJsonArray = photosJsonObject.getJSONArray("photo")
            for (i in 0 until photoJsonArray.length()) {
                val photoJsonObject = photoJsonArray.getJSONObject(i)
                val id = photoJsonObject.getString("id")
                val title = photoJsonObject.getString("title")
                try {
                    val url_s:String? = photoJsonObject.getString("url_s")
                    url_s?.let {
                        val item = PhotoItem(title, url_s)
                        items.add(item)

                    }

                }catch (ex:JSONException) {}
            }
            items
        }
    }

    fun getFlickrItems(query:String):List<PhotoItem>? {
        return getJSONString(query)?.let {
            var items = ArrayList<PhotoItem>()
            val jsonBody = JSONObject(it)
            val photosJsonObject = jsonBody.getJSONObject("photos")
            val photoJsonArray = photosJsonObject.getJSONArray("photo")
            for (i in 0 until photoJsonArray.length()) {
                val photoJsonObject = photoJsonArray.getJSONObject(i)
                val id = photoJsonObject.getString("id")
                val title = photoJsonObject.getString("title")
                val url_s = photoJsonObject.getString("url_s")

                if(!url_s.isEmpty()) {
                    val item = PhotoItem(title, url_s)
                    items.add(item)
                }
            }
            items
        }
    }

    private fun getUrlBytes(urlStr:String):ByteArray? {
        var url = URL(urlStr)
        var connection = url.openConnection() as HttpURLConnection
        var outputStr = ByteArrayOutputStream()
        var inputStr = connection.inputStream
        if(connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.i("FlickrGetter", "Reading Data")
            var bytesRead = 0
            val buffer = ByteArray(1024)
            while(inputStr.read(buffer).also { bytesRead = it } > 0) {
                Log.i("FlickrGetter", "reading")
                outputStr.write(buffer, 0, bytesRead)
            }
            outputStr.close()
            return outputStr.toByteArray()
        }

        return null
    }




}