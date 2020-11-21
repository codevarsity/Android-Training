package com.example.taskmanagerfragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class AddTaskFragment : Fragment() {

    lateinit var cancelButton: Button
    lateinit var doneButton: Button
    lateinit var addTaskEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutView = inflater.inflate(R.layout.fragment_add_task, container, false)
        cancelButton = layoutView.findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        doneButton = layoutView.findViewById(R.id.doneButton)
        doneButton.setOnClickListener {

            val intent = Intent().apply {
                putExtra("NEW_TASK", addTaskEditText.text.toString())
            }
            targetFragment?.onActivityResult(101, Activity.RESULT_OK, intent)

            fragmentManager?.popBackStack()

        }
        addTaskEditText = layoutView.findViewById(R.id.addTaskEditText)

        return layoutView
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddTaskFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}