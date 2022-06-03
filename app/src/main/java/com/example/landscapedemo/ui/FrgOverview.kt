package com.example.landscapedemo.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import retrofit2.Call
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.landscapedemo.R
import com.example.landscapedemo.adapter.NoteAdapter
import com.example.landscapedemo.adapter.StateAdapter
import com.example.landscapedemo.api.*
import com.example.landscapedemo.databinding.DialogNoteBinding
import com.example.landscapedemo.databinding.DialogUpdateNoteBinding
import com.example.landscapedemo.databinding.FrgOverviewBinding
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.*

class FrgOverview : Fragment(),NoteAdapter.NoteListener,StateAdapter.StateListener {
    val TAG = "12345"
    lateinit var binding: FrgOverviewBinding
    val service = ApiService.create()
    private val adapterNote by lazy {
        NoteAdapter(this)
    }
    private val adapterState by lazy {
        StateAdapter(context = requireContext(),this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrgOverviewBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvNote.adapter = adapterNote
        binding.rcvNote.layoutManager =
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        var project = Project()
        
        binding.rcvState.adapter = adapterState
        binding.rcvState.layoutManager =
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        updateNoteList()
        updateStateList(adapterState)

        service.getProjectDetail(FragmentProjectDetail.projectId).enqueue(object : Callback<ResponseProjectDetail> {
            override fun onResponse(call: Call<ResponseProjectDetail>?, response: Response<ResponseProjectDetail>?) {
                val base: ResponseProjectDetail? = response?.body()
                Log.d(TAG, "onResponse FrgOverview ResponseProjectDetail:")
                if (base != null) {
                    project = base.data
                    binding.tvDescribe.text = Editable.Factory.getInstance().newEditable(project.description ?: "")
                    binding.tvProjectName.text = Editable.Factory.getInstance().newEditable(project.name ?: "")
                    binding.tvDescribe.text = Editable.Factory.getInstance().newEditable(project.description ?: "")
                    binding.tvProjectId.text = project.id.toString() ?: ""
                }
            }

            override fun onFailure(call: Call<ResponseProjectDetail>?, t: Throwable?) {
                Log.d(TAG, "onFailure: Không nhận được Project Detail $t")
                Log.d(TAG, "onResponse: ${call?.request()?.url()}")
            }
        })

        //update Project
        binding.btSave.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                service.updateProject(RequestUpdateProject(
                    id = FragmentProjectDetail.projectId?:1,
                    name = binding.tvProjectName.text.toString() ?: "",
                    description = binding.tvDescribe.text.toString() ?: "",
                    endDate = project.endDate ?: "",
                    status = project.status ?: 0,
                    startDate = project.startDate?: "",
                    customer = project.customer?: "",
                    lastUpdatedUser = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    lastUpdatedTime = LocalDateTime.now().toString(),
                    archived = true)).enqueue(object: Callback<ResponseUpdateProject>{
                    override fun onResponse(call: Call<ResponseUpdateProject>?, response: Response<ResponseUpdateProject>?) {
                            Log.d(TAG, "onResponse: Update success")
                        }

                        override fun onFailure(call: Call<ResponseUpdateProject>?, t: Throwable?) {
                            Log.d(TAG, "onFailure: Update fail $t")
                            Log.d(TAG, "onResponse: ${call?.request()?.url()}")
                        }
                    })
            }
        }

        binding.btAddNote.setOnClickListener{
            openDialogNote()
        }

        binding.btAddState.setOnClickListener {
            service.addState(FragmentProjectDetail.projectId, RequestAddState(
                name = "string",
                description = "",
                status = 0,
                startDate = LocalDateTime.now().toString(),
                endDate = LocalDateTime.now().toString())).enqueue(object : Callback<ResponseAdd>{
                override fun onResponse(
                    call: Call<ResponseAdd>?,
                    response: Response<ResponseAdd>?
                ) {
                    Log.d(TAG, "onResponse FrgOverview addState: ")
                    updateStateList(adapterState)
                }

                override fun onFailure(call: Call<ResponseAdd>?, t: Throwable?) {
                    Log.d(TAG, "onFailure: Can't add a new State")
                }

            })
        }
    }

    private fun updateStateList(adapterState: StateAdapter) {
        val listState : MutableList<State> = mutableListOf()
        service.getAllStages(1,1000,FragmentProjectDetail.projectId).enqueue(object : Callback<ResponseStateList>{
            override fun onResponse(
                call: Call<ResponseStateList>?,
                response: Response<ResponseStateList>?
            ) {
                Log.d(TAG, "onResponse FrgOverview State List ${FragmentProjectDetail.projectId}: ${response?.body()}")
                for(i in response?.body()?.data!!.data){
                    listState.add(i)
                }
                adapterState.updateList(listState)
            }

            override fun onFailure(call: Call<ResponseStateList>?, t: Throwable?) {
                Log.d(TAG, "onFailure: Can't get State List")
            }

        })
    }

    private fun openDialogNote() {
        val binding = DialogNoteBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())

        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        binding.btAddNote.setOnClickListener{
            service.addNote(
                RequestAddNote(
                    content = binding.etContent.text.toString(),
                    archived = false,
                    projectId = FragmentProjectDetail.projectId,
                    pin = false
                )
            ).enqueue(object: Callback<ResponseAdd>{
                override fun onResponse(
                    call: Call<ResponseAdd>?,
                    response: Response<ResponseAdd>?
                ) {
                    Log.d(TAG, "onResponse: Add success")
                }

                override fun onFailure(call: Call<ResponseAdd>?, t: Throwable?) {
                    Log.d(TAG, "onFailure: Add fail")
                }

            })
            this.updateNoteList()
            dialog.cancel()
        }
    }

    //getNote and open dialog for update
    @SuppressLint("NotifyDataSetChanged")
    override fun getNote(note: Note) {
        val binding = DialogUpdateNoteBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
//        noteBinding.etNoteTitle.text = Editable.Factory.getInstance().newEditable(note.title)
        binding.etContent.text = Editable.Factory.getInstance().newEditable(note.content)

        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        binding.btUpdateNote.setOnClickListener{
//            note.title = noteBinding.etNoteTitle.text.toString()
            note.content = binding.etContent.text.toString()
            service.updateNote(RequestUpdateNote(
                archived = note.archived,
                content = binding.etContent.text.toString(),
                id = note.id,
                pin = note.pin,
                projectId = FragmentProjectDetail.projectId
            )).enqueue(object: Callback<ResponseUpdateNote>{
                override fun onResponse(
                    call: Call<ResponseUpdateNote>?,
                    response: Response<ResponseUpdateNote>?
                ) {
                    Log.d(TAG, "onResponse: Update note success")
                    updateNoteList()
                }

                override fun onFailure(call: Call<ResponseUpdateNote>?, t: Throwable?) {
                    Log.d(TAG, "onFailure: Update note fail")
                }
            })
            dialog.cancel()
        }

        binding.btDeleteNote.setOnClickListener {
            dialog.cancel()
        }
    }

    private fun updateNoteList(){
        val noteList: MutableList<Note> = mutableListOf()
        service.getAllNote(FragmentProjectDetail.projectId).enqueue(object: Callback<ResponseNoteList>{
            override fun onResponse(
                call: Call<ResponseNoteList>?,
                response: Response<ResponseNoteList>?
            ) {
//                Log.d(TAG, "onResponse: ${response?.body()}")
                for(i in response?.body()!!.data){
                    noteList.add(i)
                }
                adapterNote.updateNoteList(noteList)
            }

            override fun onFailure(call: Call<ResponseNoteList>?, t: Throwable?) {
                Log.d(TAG, "onFailure: Cant get Note List")
            }

        })
    }

    override fun updateState() {
        updateStateList(adapterState)
    }


}


