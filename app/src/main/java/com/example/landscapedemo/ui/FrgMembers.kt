package com.example.landscapedemo.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.landscapedemo.adapter.MemberAdapter
import com.example.landscapedemo.api.ApiService
import com.example.landscapedemo.api.Member
import com.example.landscapedemo.api.ResponseMemberList
import com.example.landscapedemo.databinding.FrgMembersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FrgMembers : Fragment() {
    lateinit var binding :FrgMembersBinding
    val service = ApiService.create()
    val TAG = "12345"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrgMembersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listMember = mutableListOf<Member>()
        val adapter = MemberAdapter()
        binding.rcv.adapter = adapter
        binding.rcv.layoutManager = LinearLayoutManager(requireContext())

        service.getAllMember(FragmentProjectDetail.projectId).enqueue(object : Callback<ResponseMemberList>{
            var listMember = mutableListOf<Member>()
            override fun onResponse(
                call: Call<ResponseMemberList>?,
                response: Response<ResponseMemberList>?
            ) {
                for (i in response?.body()?.data!!){
                    listMember.add(i)
                }
                adapter.updateList(listMember)
            }

            override fun onFailure(call: Call<ResponseMemberList>?, t: Throwable?) {
                Log.d(TAG, "onFailure: Get Member List Fail ")
            }

        })

        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val filterList = mutableListOf<Member>()
                for(i in listMember){
                    if(i.username.lowercase().contains(s.toString().lowercase())) filterList.add(i)
                }
                adapter.updateList(filterList)
            }

        })
    }
}