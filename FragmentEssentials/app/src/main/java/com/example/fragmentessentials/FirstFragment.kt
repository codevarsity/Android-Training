package com.example.fragmentessentials

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class FirstFragment : Fragment() {

    lateinit var userMessageEditText: EditText
    lateinit var sendButton: Button

    companion object {
        private val TAG = "FirstFragment"

        fun createFragment():Fragment {
            return FirstFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        val layoutView = inflater.inflate(R.layout.fragment_first, container, false)
        userMessageEditText = layoutView.findViewById(R.id.userMessageEditText)
        sendButton = layoutView.findViewById(R.id.sendButton)
        sendButton.setOnClickListener {
            loadSecondFragment()
        }
        return layoutView
    }

    fun loadSecondFragment() {
        val message = userMessageEditText.text.toString()
        val fragment = SecondFragment.newInstance(message)
        fragmentManager?.
        beginTransaction()?.
        remove(this)?.
        add(R.id.mainLayout, fragment,  "SecondFragment")?.
        commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}