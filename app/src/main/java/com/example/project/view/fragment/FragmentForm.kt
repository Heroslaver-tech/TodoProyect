package com.example.project.view.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentFormBinding
import com.example.project.viewmodel.PetViewModel

import  androidx.appcompat.app.AppCompatActivity

class FragmentForm : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private val petViewModel: PetViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater)
        binding.spinnerHour.setSelection(4)

        binding.spinnerHour.setSelection(1)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
       //obtenerRazasDePerro()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drivers()
        goBack()
    }

    private fun drivers() {



    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun goBack() {
        binding.toolbarBalls.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}