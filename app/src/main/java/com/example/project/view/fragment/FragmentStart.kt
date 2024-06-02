package com.example.project.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentStartBinding
import com.example.project.viewmodel.PetViewModel

class FragmentStart : Fragment() {

    private lateinit var binding: FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater)
        drivers()

        return binding.root
    }
    private fun drivers() {
        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentStart_to_fragmentLogin)
        }
    }
}