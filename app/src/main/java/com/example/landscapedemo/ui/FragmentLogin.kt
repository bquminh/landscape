package com.example.landscapedemo.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.landscapedemo.MainActivity
import com.example.landscapedemo.R
import com.example.landscapedemo.api.ApiService
import com.example.landscapedemo.databinding.FragmentLoginBinding

class FragmentLogin : Fragment() {
//    private lateinit var auth: FirebaseAuth
    lateinit var binding : FragmentLoginBinding
    val TAG = "12345"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
//        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("12345", "onViewCreated: ")

        // Create Service
        val service = ApiService.create()

        var hide = true
        binding.imEye.setOnClickListener {
            if (hide) {
                hide = false
                binding.imEye.setImageResource(R.drawable.ic_hide_password)
                binding.etPassword.transformationMethod = null
            } else {
                hide = true
                binding.imEye.setImageResource(R.drawable.ic_show_password)
                binding.etPassword.transformationMethod = PasswordTransformationMethod()
            }
        }

        binding.btLogin.setOnClickListener {
//            for(i in Database.listAccount) {
//                if (i.username == binding.etUsername.text.toString()
//                    && i.password == binding.etPassword.text.toString()) {
//                    var intent = Intent(requireContext(), MainActivity::class.java)

//                }

//            val requestCall = service.login(RequestBody("admin","EzTek.io10",true))
//            requestCall.enqueue(object: Callback<ResponseUpdateProject> {
//                override fun onResponse(call: Call<ResponseUpdateProject>?, response: Response<ResponseUpdateProject>?) {
//                    Log.d(TAG, "onResponse: ${response?.headers()} \n ${response?.body()}")
//                }
//
//                override fun onFailure(call: Call<ResponseUpdateProject>?, t: Throwable?) {
//                    Log.d(TAG, "onFailure: Login Fail")
//                }
//            })
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
//            }

//            auth.signInWithEmailAndPassword(binding.etUsername.text.toString().trim(),
//                binding.etPassword.text.toString().trim())
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful){
//                        updateUI()
//                    }
//                    else {
//                        Toast.makeText(requireContext(), getString(R.string.loginfail), Toast.LENGTH_SHORT).show()
//                    }
//                }

        }

        binding.tvForgotpassword.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_login_to_fragmentResetPassword)
        }

        binding.linearSignup.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_login_to_fragmentCreateAccount)
        }

    }

    fun updateUI(){

        val intent = Intent(requireActivity(),MainActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
//        if (auth.currentUser != null) {
//            updateUI()
//        }
        super.onStart()
    }

}

