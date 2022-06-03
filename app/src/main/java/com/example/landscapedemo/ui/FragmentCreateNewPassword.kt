package com.example.landscapedemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.landscapedemo.R
import com.example.landscapedemo.databinding.FragmentCreateNewPasswordBinding

class FragmentCreateNewPassword : Fragment() {
    lateinit var binding : FragmentCreateNewPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btDone.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentCreateNewPassword_to_fragment_login)
        }

    }

}

