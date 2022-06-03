package com.example.landscapedemo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.landscapedemo.adapter.ProgressAdapter
import com.example.landscapedemo.adapter.SpStateAdapter
import com.example.landscapedemo.api.*
import com.example.landscapedemo.databinding.FrgTimelineBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FrgTimeline : Fragment() {
    lateinit var binding: FrgTimelineBinding
    lateinit var adapter: ProgressAdapter
    val service = ApiService.create()
    val TAG = "12345"
    var currentState : State? = null

    private lateinit var spStateAdapter :SpStateAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FrgTimelineBinding.inflate(inflater,container,false)
        spStateAdapter = SpStateAdapter(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProgressAdapter(requireContext())
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
        binding.spState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

    }
    fun refreshState(){
        val timelineState : MutableList<GrouptaskTimeline> = mutableListOf()
        service.getTimeline(currentState!!.id!!).enqueue(object : Callback<ResponseTimeline>{
            override fun onResponse(
                call: Call<ResponseTimeline>?,
                response: Response<ResponseTimeline>?
            ) {
                Log.d(TAG, "onResponse: ${response?.body()}")
                for(i in response?.body()?.data!!){
                    timelineState.add(i)
                }
                adapter.updateList(timelineState)
            }

            override fun onFailure(call: Call<ResponseTimeline>?, t: Throwable?) {
                Log.d(TAG, "onFailure: get Timeline fail")
            }

        })
    }
}