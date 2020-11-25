package com.example.photosclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var viewPager2: ViewPager2
    lateinit var tabLayout:TabLayout
    lateinit var adapter:PhotosPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.photosViewPager)
        tabLayout = findViewById(R.id.tabLayout)
        adapter = PhotosPagerAdapter(supportFragmentManager)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = "$position"
        }.attach()

    }

    inner class PhotosPagerAdapter(fragmentManager:FragmentManager) : FragmentStateAdapter(fragmentManager, this.lifecycle) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
           return if(position == 0) {
               RecentPhotosFragment()
           } else {
               SearchPhotosFragment()
           }
        }

    }
}

