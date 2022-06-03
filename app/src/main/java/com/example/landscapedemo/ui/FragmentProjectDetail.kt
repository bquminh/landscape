package com.example.landscapedemo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.landscapedemo.R
import com.example.landscapedemo.adapter.GroupTaskAdapter
import com.example.landscapedemo.adapter.ProjectDetailAdapter
import com.example.landscapedemo.adapter.TaskAdapter
import com.example.landscapedemo.databinding.FragmentProjectDetailBinding

import com.google.android.material.tabs.TabLayoutMediator

class FragmentProjectDetail : Fragment(){
    lateinit var binding :FragmentProjectDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            projectId = FragmentProjectDetailArgs.fromBundle(it).projectId
        }
        binding = FragmentProjectDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tạo và gán giá trị cho TabLayout, ViewPager
        var adapter = ProjectDetailAdapter(requireActivity())
        binding.viewpager.adapter = adapter
        TabLayoutMediator(binding.tablayout,binding.viewpager, TabLayoutMediator.TabConfigurationStrategy
        { tab, position -> when(position){
                0->tab.text = getString(R.string.overview)
                1->tab.text = getString(R.string.task)
                2->tab.text = getString(R.string.timeline)
                3->tab.text = getString(R.string.member)
            }
        }).attach()


//        binding.tablayout.addOnTabSelectedListener(object : OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//
//        })
    }

    companion object{
        var projectId: Int = 0
    }


}