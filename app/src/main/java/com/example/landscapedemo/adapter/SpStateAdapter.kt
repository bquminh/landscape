package com.example.landscapedemo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.landscapedemo.api.State
import com.example.landscapedemo.databinding.LayoutSpstateBinding

class SpStateAdapter(var layoutInflater: LayoutInflater) : BaseAdapter() {
    var stateList : MutableList<State> = mutableListOf()

    override fun getCount(): Int {
        return stateList.size
    }

    override fun getItem(position: Int): State {
        return stateList[position]
    }

    override fun getItemId(position: Int): Long {
        return 1;
    }

    fun updateList(list: MutableList<State>){
        stateList.clear()
        stateList.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = LayoutSpstateBinding.inflate(layoutInflater,parent,false)
        binding.tvStatename.text = stateList[position].name
        return binding.root
    }
}