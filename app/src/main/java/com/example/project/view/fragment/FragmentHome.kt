package com.example.project.view.fragment

import android.os.Bundle
<<<<<<< Updated upstream
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
=======
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project.databinding.FragmentHomeBinding
import com.example.project.databinding.FragmentLoginBinding
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.example.project.R
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class FragmentHome : Fragment() {

>>>>>>> Stashed changes
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< Updated upstream
        binding = FragmentHomeBinding.inflate(inflater)
        drivers()

        return binding.root
    }
    private fun drivers() {
        binding.getStartedButton.setOnClickListener {
=======
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonGetStarted: Button = view.findViewById(R.id.getStartedButton)

        buttonGetStarted.setOnClickListener {
>>>>>>> Stashed changes
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentLogin)
        }
    }
}