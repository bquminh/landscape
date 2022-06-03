package com.example.landscapedemo.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.landscapedemo.databinding.LayoutProjectBinding
import com.example.landscapedemo.adapter.ProjectAdapter.ProjectViewHolder
import com.example.landscapedemo.api.Project
import com.example.landscapedemo.databinding.DialogConfirmDeleteBinding

class ProjectAdapter(var context: Context) : RecyclerView.Adapter<ProjectViewHolder>(){
//    var database = Firebase.database.reference
//    var auth = Firebase.auth
    var detail : (projectId : Int) -> Unit = {}
    var mList: MutableList<com.example.landscapedemo.api.Project> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder{

        return ProjectViewHolder(
            LayoutProjectBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(mList[position])
        holder.binding.projectLayout.setOnClickListener{
            detail(mList[position].id)
        }

        holder.binding.projectLayout.setOnLongClickListener {
            openConfirmDeleteDialog(mList[position])
            false
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<Project>){
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }


    class ProjectViewHolder(val binding: LayoutProjectBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project){
            binding.tvProjectName.text = project.name
            binding.tvDescribe.text = project.description
            binding.tvTaskLate.text = "Task late: " + project.tasklate.toString()
            binding.tvTaskRemain.text = "Task remain: " + project.taskremain.toString()

        }
    }

    @SuppressLint("SetTextI18n")
    private fun openConfirmDeleteDialog(project: com.example.landscapedemo.api.Project){
        val binding = DialogConfirmDeleteBinding .inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)

        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        binding.tvProjectName.text = project.name + " ?"
        binding.btCancel.setOnClickListener {
            dialog.cancel()
        }

        binding.btYes.setOnClickListener {
//            database.child("project").child(project.id).removeValue()
            dialog.cancel()
        }
    }


}
