package com.example.landscapedemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.landscapedemo.api.GrouptaskTimeline
import com.example.landscapedemo.databinding.LayoutProgressBinding

class ProgressAdapter(var context: Context) : RecyclerView.Adapter<ProgressAdapter.GroupTaskViewHolder>() {

    var listGroupTask : MutableList<GrouptaskTimeline> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupTaskViewHolder {
        return GroupTaskViewHolder(LayoutProgressBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: GroupTaskViewHolder, position: Int) {
        holder.bind(listGroupTask[position])

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<GrouptaskTimeline>){
        listGroupTask.clear()
        listGroupTask.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listGroupTask.size
    }

    class GroupTaskViewHolder(val binding: LayoutProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(groupTask: GrouptaskTimeline){
            binding.tvGroupTaskname.text = groupTask.groupname
            binding.progressBar.max = groupTask.countsum
            binding.progressBar.progress = groupTask.countdone
        }
    }

}


