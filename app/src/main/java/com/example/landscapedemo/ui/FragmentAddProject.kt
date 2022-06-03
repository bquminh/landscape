package com.example.landscapedemo.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.landscapedemo.databinding.FragmentAddProjectBinding
import java.util.*

class FragmentAddProject : Fragment() {
//    private lateinit var database: DatabaseReference
//    private lateinit var auth: FirebaseAuth
    lateinit var binding : FragmentAddProjectBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProjectBinding.inflate(inflater,container,false)
//        auth = Firebase.auth
//        database = Firebase.database.reference
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etStartDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                binding.etStartDate.setText("$year-$monthOfYear-$dayOfMonth")
            }, year, month, day)
            dpd.show()
        }

        binding.etEndDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                binding.etEndDate.setText("$year-$monthOfYear-$dayOfMonth")
            }, year, month, day)
            dpd.show()
        }

        binding.btCreate.setOnClickListener {
            val projectName = binding.etProjectname.text.toString()
            val description = binding.etDescription.text.toString()
            val startDate = binding.etStartDate.text.toString()
            val endDate = binding.etEndDate.text.toString()
//            val user = auth.currentUser!!
            if(projectName.isEmpty() || description.isEmpty() || startDate.isEmpty() || endDate.isEmpty()){
                Toast.makeText(requireContext(), "Fill in the blank", Toast.LENGTH_SHORT).show()
            }
            else{
//                val project = Project(name = projectName, description = description, startDate = startDate,
//                    endDate = endDate, createUser = user.uid)
//                database.child("project").child(project.id).setValue(project)
                requireActivity().onBackPressed()
            }

        }
    }

}

