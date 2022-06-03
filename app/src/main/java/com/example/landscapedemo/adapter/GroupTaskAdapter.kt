package com.example.landscapedemo.adapter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landscapedemo.R
import com.example.landscapedemo.api.*
import com.example.landscapedemo.databinding.DialogGrouptaskDetailBinding
import com.example.landscapedemo.databinding.LayoutGroupTaskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class GroupTaskAdapter(var context: Context, val listener:GrouptaskListener) : RecyclerView.Adapter<GroupTaskAdapter.GroupTaskViewHolder>(),TaskAdapter.TaskListener {
    var listGroupTask : MutableList<Grouptask> = mutableListOf()

    val service = ApiService.create()
    val TAG = "12345"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupTaskViewHolder {
        return GroupTaskViewHolder(LayoutGroupTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: GroupTaskViewHolder, position: Int) {
        holder.bind(listGroupTask[position])

        var check  = true

        val taskList = mutableListOf<Task>()
        service.getAllTasks(listGroupTask[position].id).enqueue(object: Callback<ResponseTaskList>{
            override fun onResponse(
                call: Call<ResponseTaskList>?,
                response: Response<ResponseTaskList>?
            ) {
                for(i in response?.body()?.data!!){
                    taskList.add(i)
                }
                val adapter = TaskAdapter(context,taskList,this@GroupTaskAdapter)
                holder.binding.rcvTask.adapter = adapter
                holder.binding.rcvTask.layoutManager = LinearLayoutManager(context)
//                    adapter.updateList(taskList)
            }

            override fun onFailure(call: Call<ResponseTaskList>?, t: Throwable?) {
                Log.d(TAG, "onFailure: Cant get Task list")
            }

        })

//        updateTaskList(listGroupTask[position].id)
        holder.binding.linearGrouptask.setOnClickListener {
            if(check){
                check = false
                holder.binding.ivArrow.setImageResource(R.drawable.ic_arrow_down)
                holder.binding.linearTasks.visibility = View.VISIBLE
                holder.binding.ivInfo.visibility = View.VISIBLE
            }else {
                check = true
                holder.binding.ivArrow.setImageResource(R.drawable.ic_arrow_right)
                holder.binding.linearTasks.visibility = View.GONE
                holder.binding.ivInfo.visibility = View.INVISIBLE
            }
        }
        holder.binding.linearAdd.setOnClickListener {
            service.addTask(RequestAddTask(
                name = "string",
                description = "string",
                endDate= "2022-06-03T02:14:23.523Z",
                status= 0,
                assigneeId= "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                priority= 0,
                grouptaskId= listGroupTask[position].id
            )).enqueue(object: Callback<ResponseAdd>{
                override fun onResponse(
                    call: Call<ResponseAdd>?,
                    response: Response<ResponseAdd>?
                ) {
                    Toast.makeText(context,"Add success",Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseAdd>?, t: Throwable?) {
                    Log.d(TAG, "onFailure: Cant add a new Task")
                }

            })
            listener.updateGrouptask()
        }

        holder.binding.ivInfo.setOnClickListener {
            openGroupTaskInfoLayout(listGroupTask[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Grouptask>) {
        listGroupTask.clear()
        listGroupTask.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listGroupTask.size
    }

    class GroupTaskViewHolder(val binding: LayoutGroupTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(groupTask : Grouptask){
            binding.tvTaskgroupname.text = groupTask.name
        }
    }

    @SuppressLint("SetTextI18n")
    private fun openGroupTaskInfoLayout(groupTask: Grouptask) {
        val binding = DialogGrouptaskDetailBinding.inflate(LayoutInflater.from(context))
        val builder = Dialog(context, R.style.Theme_LandscapeDemo)
        builder.setContentView(binding.root)
        builder.show()
        val status = mutableListOf("Open","Progressing","Close","ReOpen")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item,status)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spState.text = groupTask.stageId.toString()
//        database.child("state").child(groupTask.stateId).addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var state:State = snapshot.getValue(State::class.java)!!
//                binding.spState.text = state.name
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("12345", "onCancelled: $error")
//            }
//
//        })

        binding.tvGroupTaskid.text = groupTask.id.toString()
        binding.spStatus.adapter = adapter
        binding.spStatus.setSelection(groupTask.status)
        binding.etStartDate.text = Editable.Factory.getInstance().newEditable(groupTask.startDate)
        binding.etEndDate.text = Editable.Factory.getInstance().newEditable(groupTask.endDate)
        binding.etGroupTaskname.text = Editable.Factory.getInstance().newEditable(groupTask.name)
        binding.etDescription.text = Editable.Factory.getInstance().newEditable(groupTask.description)

//        database.child("profile").child(groupTask.createUser)
//            .addValueEventListener(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    var profile: Profile = snapshot.getValue(Profile::class.java)!!
//                    binding.tvMemberName.text = profile.username
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.d("12345", "onCancelled: $error")
//                }
//
//            })

        binding.etStartDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                binding.etStartDate.setText("Start date: $dayOfMonth-$monthOfYear-$year")
            }, year, month, day)
            dpd.show()
        }

        binding.etEndDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                binding.etEndDate.setText("End date: $dayOfMonth-$monthOfYear-$year")
            }, year, month, day)
            dpd.show()
        }

        binding.btCancel.setOnClickListener {
            builder.cancel()
        }

        binding.btSave.setOnClickListener {
            groupTask.name = binding.etGroupTaskname.text.toString()
            groupTask.status = binding.spStatus.selectedItemPosition
            groupTask.description = binding.etDescription.text.toString()
            groupTask.startDate = binding.etStartDate.text.toString()
            groupTask.endDate = binding.etEndDate.text.toString()

            service.updateGrouptask(groupTask).enqueue(object: Callback<ResponseUpdateGrouptask>{
                override fun onResponse(
                    call: Call<ResponseUpdateGrouptask>?,
                    response: Response<ResponseUpdateGrouptask>?
                ) {
                    listener.updateGrouptask()
                    Log.d(TAG, "onResponse: Update Success")
                }

                override fun onFailure(call: Call<ResponseUpdateGrouptask>?, t: Throwable?) {
                    Log.d(TAG, "onFailure: Update Fail")
                }

            })
            builder.cancel()
        }

        binding.btDelete.setOnClickListener {
//            database.child("groupTask").child(groupTask.id).removeValue()
            builder.cancel()
        }

    }

    interface GrouptaskListener{
        fun updateGrouptask()
    }

    override fun updateTask() {
        listener.updateGrouptask()
    }

}


