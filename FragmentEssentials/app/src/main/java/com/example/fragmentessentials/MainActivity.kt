package com.example.fragmentessentials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            loadFirstFragment()
        }

        //logic
    }

    fun loadFirstFragment() {
        //create the fragment
        val fragment = FirstFragment.createFragment()

        //get access to the FragmentManager
        supportFragmentManager.
        beginTransaction().
        add(R.id.mainLayout, fragment, "FirstFragment").
        commit()
    }
}