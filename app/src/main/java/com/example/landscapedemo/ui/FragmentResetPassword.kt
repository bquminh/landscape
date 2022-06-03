package com.example.landscapedemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.landscapedemo.R
import com.example.landscapedemo.databinding.FragmentResetPasswordBinding

class FragmentResetPassword : Fragment() {
    lateinit var binding : FragmentResetPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btNext.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentResetPassword_to_fragmentCreateNewPassword)
        }

    }

}

