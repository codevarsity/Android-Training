package com.example.activityessentials

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var userMessageTextView:TextView
    lateinit var userMessageEditText: EditText
    lateinit var sendButton: Button

    companion object {
        private val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "onCreate")

        userMessageTextView = findViewById(R.id.userMessageTextView)
        userMessageEditText = findViewById(R.id.userMessageEditText)
        sendButton = findViewById(R.id.sendButton)
        sendButton.setOnClickListener {
            Log.i("MainActivity", "Send Button clicked")
            userMessageTextView.text = userMessageEditText.text.toString()

            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("USER_MESSAGE", userMessageEditText.text.toString())

                data = Uri.parse("http://www.google.com")
            }

            startActivity(intent)
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