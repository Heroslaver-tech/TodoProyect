package com.example.project.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentRegisterBinding
import com.example.project.viewmodel.LoginViewModel


class FragmentRegister : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()
        setupTextWatchers()
        return binding.root
    }

    //drivers and bottom disable
    private fun setup() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        //Init bottom in disable
        binding.btnRegister.isEnabled = false
    }

    //go to login
    private fun goToLogin(){
        findNavController().navigate(R.id.action_fragmentRegister_to_fragmentLogin)
    }

    //Register the user
    private fun registerUser(){
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()

        // Verify if email and pass is not empty
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(context, "Please complete all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Verify if password have minimum 6 characters
        if (pass.length < 6) {
            Toast.makeText(context, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            return
        }

        loginViewModel.registerUser(email,pass) { isRegister ->
            if (isRegister) {
                Toast.makeText(context, "Success Register", Toast.LENGTH_SHORT).show()
                goToLogin()
            } else {
                Toast.makeText(context, "Register Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInputs()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etEmail.addTextChangedListener(textWatcher)
        binding.etPass.addTextChangedListener(textWatcher)
    }

    private fun validateInputs() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()

        binding.btnRegister.isEnabled = email.isNotEmpty() && pass.isNotEmpty() && pass.length >= 6
    }
}