package com.example.landscapedemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.landscapedemo.api.Member
import com.example.landscapedemo.databinding.LayoutMemberBinding
import com.example.landscapedemo.model.Profile

class MemberAdapter() : RecyclerView.Adapter<MemberAdapter.GroupTaskViewHolder>() {

    var listMember: MutableList<Member> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupTaskViewHolder {
        return GroupTaskViewHolder(LayoutMemberBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: GroupTaskViewHolder, position: Int) {
        holder.bind(listMember[position])

    }

    override fun getItemCount(): Int {
        return listMember.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<Member>){
        listMember.clear()
        listMember.addAll(list)
        notifyDataSetChanged()
    }

    class GroupTaskViewHolder(val binding: LayoutMemberBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(member : Member){
            binding.tvMemberName.text = member.username
            binding.tvPosition.text = member.mainPosition
//            binding.ivAva.setImageResource(member.avatar)
        }
    }

}


