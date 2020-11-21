package com.example.taskmanagerfragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class TaskListFragment : Fragment() {

    var taskItems:ArrayList<String> = ArrayList<String>()
    lateinit var addButton:Button
    lateinit var taskListTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            taskItems.add("Get Milk")
        }else {
            taskItems.clear()
            val items = savedInstanceState.getStringArrayList("ITEMS")
            for(item in items!!) {
                taskItems.add(item)
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("ITEMS", taskItems)
    }

    fun convertToString(items:ArrayList<String>):String {
        var buffer = StringBuffer()
        for(item in items) {
            buffer.append(item)
            buffer.append("\n")
        }

        return buffer.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutView = inflater.inflate(R.layout.fragment_task_list, container, false)
        addButton = layoutView.findViewById(R.id.addTaskButton)
        addButton.setOnClickListener {
            val fragment = AddTaskFragment()
            fragment.setTargetFragment(this, 101)

            fragmentManager?.beginTransaction()?.remove(this)?.add(R.id.mainLayout, fragment)?.
            addToBackStack(null)?.commit()
        }
        taskListTextView = layoutView.findViewById(R.id.taskListTextView)
        updateUI()

        return layoutView
    }

    fun updateUI() {
        val tasksString = convertToString(taskItems)
        taskListTextView.text = tasksString
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == Activity.RESULT_OK) {
            if ( data != null) {
                val newTask = data.getStringExtra("NEW_TASK")
                if(newTask != null) {
                    taskItems.add(newTask)
                    updateUI()
                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            TaskListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}