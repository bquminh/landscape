package com.example.landscapedemo.adapter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landscapedemo.R
import com.example.landscapedemo.api.ApiService
import com.example.landscapedemo.api.ResponseUpdateState
import com.example.landscapedemo.api.State
import com.example.landscapedemo.databinding.DialogStateDetailBinding
import com.example.landscapedemo.databinding.LayoutStateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class StateAdapter(var context: Context, val listener: StateListener) : RecyclerView.Adapter<StateAdapter.StateViewHolder>(){
    var stateList : MutableList<State> = mutableListOf()
    val TAG = "12345"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder{
        return StateViewHolder(
            LayoutStateBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.bind(stateList[position])
        holder.binding.constraintState.setOnClickListener{
            openDialogStateDetail(stateList[position])
        }
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<State>){
        stateList.clear()
        stateList.addAll(list)
        notifyDataSetChanged()
    }

    class StateViewHolder(val binding: LayoutStateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(state : State){
            binding.tvStatename.text = state.name
//            binding.tvStartdate.text = state.startDate
//            binding.tvEnddate.text = state.endDate
        }
    }

    @SuppressLint("SetTextI18n")
    private fun openDialogStateDetail(state: State) {
        val commentAdapter = CommentAdapter()
        val binding = DialogStateDetailBinding.inflate(LayoutInflater.from(context))
        val builder = Dialog(context,R.style.Theme_LandscapeDemo)
        builder.setContentView(binding.root)
        builder.show()

        val service = ApiService.create()

        binding.etStatename.text = Editable.Factory.getInstance().newEditable(state.name)
        binding.etDescription.text = Editable.Factory.getInstance().newEditable(state.description)
        binding.tvStartdate.text = state.startDate
        binding.tvEnddate.text = state.endDate
        binding.rcvComment.adapter = commentAdapter
        binding.rcvComment.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        binding.tvId.text = state.id.toString()

//        database.child("comment").orderByChild("stateId").equalTo(state.id).addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val listComment : MutableList<Comment> = mutableListOf()
//                for(i in snapshot.children){
//                    val comment: Comment = i.getValue(Comment::class.java)!!
//                    listComment.add(comment)
//                }
//                commentAdapter.updateList(listComment)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d(TAG, "onCancelled: $error")
//            }
//
//        })

        binding.ivSend.setOnClickListener {
            if(binding.etComment.text != null){
//                val comment = Comment(content = binding.etComment.text.toString(),
//                    createUser = auth.currentUser!!.uid,
//                    stateId = state.id)
//                database.child("comment").setValue(comment)
//                commentAdapter.addComment(comment)
            }
        }

        binding.tvStartdate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                binding.tvStartdate.text = "$year-$monthOfYear-$dayOfMonth"
            }, year, month, day)
            dpd.show()
        }

        binding.tvEnddate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                binding.tvEnddate.text = "$year-$monthOfYear-$dayOfMonth"
            }, year, month, day)
            dpd.show()
        }

        binding.ivCheck.setOnClickListener {
            state.name = binding.etStatename.text.toString()
            state.description = binding.etDescription.text.toString()
            state.startDate = binding.tvStartdate.text.toString()
            state.endDate = binding.tvEnddate.text.toString()

            service.updateState(state).enqueue(object: Callback<ResponseUpdateState>{
                override fun onResponse(
                    call: Call<ResponseUpdateState>?,
                    response: Response<ResponseUpdateState>?
                ) {
                    listener.updateState()
                    Toast.makeText(context,"Update Complete",Toast.LENGTH_SHORT).show()
                    builder.cancel()
                }

                override fun onFailure(call: Call<ResponseUpdateState>?, t: Throwable?) {
                    Log.d(TAG, "onFailure: Can't update State Detail")
                }

            })
        }

    }

    interface StateListener{
        fun updateState()
    }

}
