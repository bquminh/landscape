package com.example.landscapedemo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.landscapedemo.api.Note
import com.example.landscapedemo.databinding.LayoutNoteBinding

class NoteAdapter(private val listener : NoteListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var listNote : MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNote[position])

        holder.binding.tvNotetitle.setOnClickListener {
            listener.getNote(listNote[position])

        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNoteList(list: MutableList<com.example.landscapedemo.api.Note>){
        listNote.clear()
        listNote.addAll(list)
        notifyDataSetChanged()
    }

    class NoteViewHolder(val binding: LayoutNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note : Note){
            binding.tvNotetitle.text = note.content
        }
    }

    interface NoteListener{
        fun getNote(note: Note)
    }
}