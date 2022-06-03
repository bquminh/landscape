package com.example.landscapedemo.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.landscapedemo.databinding.LayoutCommentBinding
import com.example.landscapedemo.model.Comment
import com.example.landscapedemo.model.Profile
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
//    val database = Firebase.database.reference
    var listComment : MutableList<Comment> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(LayoutCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(listComment[position])
        holder.binding.btClose.setOnClickListener {
//            database.child("comment").child(listComment[position].id).removeValue()
            listComment.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listComment.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addComment(comment: Comment){
        listComment.add(comment)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<Comment>){
        listComment.clear()
        listComment.addAll(list)
        notifyDataSetChanged()
    }

    class CommentViewHolder(val binding: LayoutCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment){
            val database = Firebase.database.reference
            val auth = Firebase.auth

//            database.child("profile").child(auth.currentUser!!.uid).addValueEventListener(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val profile :Profile = snapshot.getValue(Profile::class.java)!!
//                    binding.tvProfilename.text = profile.name
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.d("12345", "onCancelled: ")
//                }
//
//            })

            binding.tvComment.text = comment.content
            binding.tvCreatedate.text = comment.createTime
        }


    }

}