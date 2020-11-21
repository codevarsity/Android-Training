package com.example.taskmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var addTaskButton: Button
    lateinit var taskListTextView: TextView

    var taskItems = mutableListOf<String>("Get milk")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskListTextView = findViewById(R.id.taskListTextView)
        addTaskButton = findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, 101)
        }

        if(savedInstanceState == null) {
            Log.i("MainActivity", "Activity launched")
        } else {
            Log.i("MainActivity", "Activity re-launched")
            taskItems.clear()
            val itemsArrayList = savedInstanceState.getStringArrayList("ITEMS")
            if(itemsArrayList != null) {
                for (item in itemsArrayList) {
                    taskItems.add(item)
                }
            }
        }

        updateUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 101 && resultCode == RESULT_OK) {
            if(data != null) {
                val item = data.getStringExtra("NEW_TASK")
                taskItems.add(item!!)
                updateUI()
            }
        }
    }

    private fun updateUI() {
        val textString = arrayToString(taskItems)
        taskListTextView.text = textString
    }

    private fun arrayToString(items:List<String>):String {
        val buffer = StringBuffer()
        for(item in items) {
            buffer.apply {
                append(item)
                append("\n")
            }
        }
        return buffer.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("MainActivity", "onSaveInstanceState")
        outState.putStringArrayList("ITEMS", ArrayList(taskItems))
    }
 }