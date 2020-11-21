package com.example.taskmanager

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddTaskActivity : AppCompatActivity() {

    lateinit var newTaskEditText: EditText
    lateinit var cancelButton:Button
    lateinit var doneButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        newTaskEditText = findViewById(R.id.newTaskEditText)
        cancelButton = findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        doneButton = findViewById(R.id.doneButton)
        doneButton.setOnClickListener {
            val intent = Intent().apply {

                putExtra("NEW_TASK", newTaskEditText.text.toString())

            }

            //send a result back
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }
}