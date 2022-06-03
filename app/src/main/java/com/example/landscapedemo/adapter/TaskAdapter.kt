package com.example.landscapedemo.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.landscapedemo.R
import com.example.landscapedemo.api.ApiService
import com.example.landscapedemo.api.ResponseUpdateTask
import com.example.landscapedemo.api.Task
import com.example.landscapedemo.databinding.DialogTaskDetailBinding
import com.example.landscapedemo.databinding.LayoutTaskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskAdapter(var context: Context, private var taskList: MutableList<Task>,val listener: TaskListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    val TAG = "12345"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder{
        return TaskViewHolder(
            LayoutTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
        holder.binding.linearTask.setOnClickListener{
            openDialogTaskDetail(taskList[position])
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: MutableList<Task>){
        taskList.clear()
        taskList.addAll(list)
        notifyDataSetChanged()
    }

    class TaskViewHolder(val binding: LayoutTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task : Task){
            binding.tvTaskname.text = task.name
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openDialogTaskDetail(task : Task) {
        val binding = DialogTaskDetailBinding.inflate(LayoutInflater.from(context))
        val builder = Dialog(context,R.style.Theme_LandscapeDemo)
        builder.setContentView(binding.root)
        builder.show()

        binding.tvTaskname.text = Editable.Factory.getInstance().newEditable(task.name)

        binding.ivBack.setOnClickListener {
            task.name = binding.tvTaskname.text.toString()
            val service = ApiService.create()
            service.updateTask(task).enqueue(object : Callback<ResponseUpdateTask>{
                override fun onResponse(
                    call: Call<ResponseUpdateTask>?,
                    response: Response<ResponseUpdateTask>?
                ) {
                    Log.d(TAG, "onResponse: Save success")
                }

                override fun onFailure(call: Call<ResponseUpdateTask>?, t: Throwable?) {
                    Log.d(TAG, "onFailure: Update fail")
                }

            })
            listener.updateTask()
            builder.cancel()
        }
    }

    interface TaskListener{
        fun updateTask()
    }

}
