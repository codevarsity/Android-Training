package com.example.applicationessentials

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val mainView = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
//        setContentView(mainView)

        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_main)
    }

    fun dialPhone(view: View) {
        val intent = Intent(Intent.ACTION_DIAL)
        startActivity(intent)
    }

    fun takePicture(view:View) {
        //implicit intents
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    fun launchService(view:View) {
        //exlicit intents
        val intent = Intent(this, FileDownloaderService::class.java)
        startService(intent)
    }

    fun launchSecondActivity(view:View) {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    fun callFinish(view:View) {
        finish()

    }
    fun browseWeb(view:View) {
        val link = Uri.parse("http://www.google.com")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = link
        startActivity(intent)
    }
}