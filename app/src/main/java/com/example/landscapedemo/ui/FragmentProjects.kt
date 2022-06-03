package com.example.landscapedemo.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.landscapedemo.R
import com.example.landscapedemo.adapter.ProjectAdapter
import com.example.landscapedemo.api.ApiService
import com.example.landscapedemo.api.Project
import com.example.landscapedemo.api.ResponseProjectList
import com.example.landscapedemo.databinding.FragmentProjectsMenuBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentProjects : Fragment() {
//    private var database: DatabaseReference = Firebase.database.reference
//    private  var auth: FirebaseAuth = Firebase.auth
    lateinit var binding: FragmentProjectsMenuBinding
    val TAG = "12345"
    val adapter by lazy {
        ProjectAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectsMenuBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listProject: MutableList<Project> = mutableListOf()

        val service = ApiService.create()
        service.getDashboardProjectAsync().enqueue(object: Callback<ResponseProjectList>{
            override fun onResponse(call: Call<ResponseProjectList>?, response: Response<ResponseProjectList>?) {
                val responseProjectList: ResponseProjectList? = response?.body()
//                Log.d(TAG, "onResponse FragmentProjects: $responseProjectList")
                listProject.clear()
                for(i in responseProjectList!!.data){
                    listProject.add(i)
                }
                adapter.updateList(listProject)
            }

            override fun onFailure(call: Call<ResponseProjectList>?, t: Throwable?) {
                Log.d(TAG, "onFailure: Không nhận được Dashboard $t")
            }

        })

//        database.child("project").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                listProject.clear()
//                for (i in snapshot.children) {
//                    try{
//                        val project: Project = i.getValue(Project::class.java)!!
//                        listProject.add(project)
//                    }
//                    catch (e:Exception){
//                        Log.d(TAG, "onDataChangeProjects: $e")
//                    }
//
//                }
//                adapter.updateList(listProject)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d(TAG, "onCancelled: ")
//            }
//
//        })

        binding.etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                adapter.updateList(filterList(listProject, s.toString()))
            }

        })
        //Khởi tạo adapter Project
        binding.rcv.adapter = adapter
        binding.rcv.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)

        binding.btAdd.setOnClickListener {
            //Chuyển sang fragment thêm project
            findNavController().navigate(R.id.add_project)
        }

        adapter.detail = {
            //Chuyển sang fragent chi tiết dự án
            findNavController()
                .navigate(
                    FragmentProjectsDirections
                        .actionFragmentProjectsToFragmentProjectDetail(it)
                )
        }
    }

    private fun filterList(listProject: MutableList<Project>, s: String): MutableList<com.example.landscapedemo.api.Project> {
        val list : MutableList<Project> = mutableListOf()
        for(i in listProject){
            if(i.name.lowercase().contains(s.lowercase())){
                list.add(i)
            }
        }
        return list
    }
}

