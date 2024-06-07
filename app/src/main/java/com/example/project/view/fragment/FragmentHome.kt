package com.example.project.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        drivers()

        return binding.root
    }
    private fun drivers() {
        binding.getStartedButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentLogin)
        }
    }

}