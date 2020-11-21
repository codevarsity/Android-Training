package com.example.fragmentessentials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class SecondFragment() : Fragment() {

    lateinit var userMessageTextView: TextView
    lateinit var backButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutView = inflater.inflate(R.layout.fragment_second, container, false)
        userMessageTextView = layoutView.findViewById(R.id.userMessageTextView)
        backButton = layoutView.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        updateUI()
        return layoutView
    }

    fun updateUI() {
        val message = arguments?.getString("MESSAGE")
        if(message != null) {
            userMessageTextView.text = message
        }
    }

    companion object {
        fun newInstance(message:String):Fragment {
            return SecondFragment().apply {
                val bundle = Bundle().apply {
                    putString("MESSAGE", message)
                }
                arguments = bundle
            }
        }
    }
}