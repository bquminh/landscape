package com.example.landscapedemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.landscapedemo.ui.FrgMembers
import com.example.landscapedemo.ui.FrgOverview
import com.example.landscapedemo.ui.FrgTasks
import com.example.landscapedemo.ui.FrgTimeline

class ProjectDetailAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> FrgOverview()
            1-> FrgTasks()
            2-> FrgTimeline()
            3-> FrgMembers()
            else -> FrgOverview()
        }
    }
}