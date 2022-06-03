package com.example.landscapedemo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.landscapedemo.adapter.GroupTaskAdapter
import com.example.landscapedemo.adapter.SpStateAdapter
import com.example.landscapedemo.api.*
import com.example.landscapedemo.databinding.FrgTasksBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FrgTasks : Fragment(), GroupTaskAdapter.GrouptaskListener {
    lateinit var binding: FrgTasksBinding
    lateinit var adapter: GroupTaskAdapter
    private val service = ApiService.create()
    private val TAG = "12345"
    var currentState : State? = null
    private lateinit var spStateAdapter :SpStateAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FrgTasksBinding.inflate(inflater,container,false)
        spStateAdapter = SpStateAdapter(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GroupTaskAdapter(requireContext(),this)
        binding.rcv.adapter = adapter
        binding.rcv.layoutManager = LinearLayoutManager(requireContext())
        binding.spState.adapter = spStateAdapter
        val listState : MutableList<State> = mutableListOf()

        service.getAllStages(1,1000,FragmentProjectDetail.projectId).enqueue(object :
            Callback<ResponseStateList> {
            override fun onResponse(
                call: Call<ResponseStateList>?,
                response: Response<ResponseStateList>?
            ) {
//                Log.d(TAG, "onResponse FrgOverview State List ${FragmentProjectDetail.projectId}: ${response?.body()}")
                for(i in response?.body()?.data!!.data){
                    listState.add(i)
                }
                spStateAdapter.updateList(listState)
            }

            override fun onFailure(call: Call<ResponseStateList>?, t: Throwable?) {
                Log.d(TAG, "onFailure: Can't get State List")
            }

        })

        if(listState.isNotEmpty()) currentState = listState[0]
        binding.spState.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentState = listState[position]
                refreshState()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        if(listState.isNotEmpty()) refreshState()

        binding.btAddGroupTask.setOnClickListener {
            if(listState.isNotEmpty()){

                service.addGrouptask(currentState?.id!!, RequestAddGrouptask(
                    description = "string",
                    endDate = "2022-06-02T02:39:09.656Z",
                    name = "string",
                    status = 0,
                    startDate = "2022-06-02T02:39:09.656Z"
                    )).enqueue(object : Callback<ResponseAdd>{
                    override fun onResponse(
                        call: Call<ResponseAdd>?,
                        response: Response<ResponseAdd>?
                    ) {
                        Log.d(TAG, "onResponse FrgTasks: Add success")
                    }
    
                    override fun onFailure(call: Call<ResponseAdd>?, t: Throwable?) {
                        Log.d(TAG, "onFailure FrgTasks: Cant create a Grouptask")
                    }
    
                })
                refreshState()
            }
        }

    }
    fun refreshState(){
        val groupTaskList : MutableList<Grouptask> = mutableListOf()
        service.getAllGrouptasks(1,1000, currentState!!.id!!).enqueue(object : Callback<ResponseGrouptaskList>{
            override fun onResponse(
                call: Call<ResponseGrouptaskList>?,
                response: Response<ResponseGrouptaskList>?
            ) {
                for(i in response?.body()?.data?.data!!){
                    groupTaskList.add(i)
                }
                adapter.updateList(groupTaskList)
            }

            override fun onFailure(call: Call<ResponseGrouptaskList>?, t: Throwable?) {
                Log.d(TAG, "onFailure FrgTasks: Cant get Grouptask List")
            }

        })
    }

    override fun updateGrouptask() {
        refreshState()
    }

}