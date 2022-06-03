package com.example.landscapedemo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.fragment.app.Fragment
import com.example.landscapedemo.MainActivity
import com.example.landscapedemo.R
import com.example.landscapedemo.databinding.FragmentCreateAccountBinding
import com.example.landscapedemo.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class FragmentCreateAccount : Fragment() {
//    private lateinit var database: DatabaseReference
//    private lateinit var auth: FirebaseAuth
    private lateinit var binding : FragmentCreateAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccountBinding.inflate(inflater,container,false)
//        auth = Firebase.auth
//        database = Firebase.database.reference
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btSignup.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmpassword.text.toString()
            if(username.length < 6 || password != confirmPassword || password.length < 6)
                Toast.makeText(requireContext(), getString(R.string.validate), Toast.LENGTH_SHORT).show()
//            else auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener {
//                if (it.isSuccessful){
//                    val user = auth.currentUser!!
//                    val profile = Profile(id = user.uid,username = username, password = password)
//                    Log.d("12345", "onViewCreated: $profile")
//                    val intent : Intent = Intent(requireActivity(), MainActivity::class.java)
//                    database.child("profile").child(user.uid).setValue(profile)
//                    startActivity(intent)
//                } else{
//                    Toast.makeText(requireContext(), "Signing up is Unsuccess ", Toast.LENGTH_SHORT).show()
//                }
//
//            }
        }
    }
}