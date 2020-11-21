package com.example.activityessentials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class SecondActivity : AppCompatActivity() {

    lateinit var backButton:Button
    lateinit var userMessageTextView:TextView

    companion object {
        private val TAG = "SecondActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.i(TAG, "onCreate")

        userMessageTextView = findViewById(R.id.userMessageTextView)
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        //get access to the launching intent
        val launchingIntent = getIntent()
        if(launchingIntent.extras != null) {
            val message = launchingIntent.getStringExtra("USER_MESSAGE")
            if (message != null) {
                Log.i(TAG, message)
                Log.i(TAG, launchingIntent.data.toString())
                userMessageTextView.text = message
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}