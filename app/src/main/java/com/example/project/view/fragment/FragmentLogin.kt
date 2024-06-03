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
import com.example.project.databinding.FragmentLoginBinding
import com.example.project.viewmodel.LoginViewModel


class FragmentLogin : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()
        sesion()
        drivers()
        setupTextWatchers()
        return binding.root
    }

    private fun setup() {

        // Inicialmente deshabilitar el botón de registro
        binding.btnLogin.isEnabled = false
    }
    private fun drivers() {
        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegister)
        }
    }

    //go to home TODOs
    private fun goToHome(){
        findNavController().navigate(R.id.action_fragmentLogin_to_fragmentMain)
    }

    //Verify the login in Firebase
    private fun loginUser(){
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()

        // Verify if email and pass is not empty
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(context, "Please complete all fields", Toast.LENGTH_SHORT).show()
            return
        }

        loginViewModel.loginUser(email,pass){ isLogin ->
            if (isLogin){
                goToHome()
            }else {
                Toast.makeText(context, "Bad credentials\n" +
                        "Check the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Verify is have a open session already
    private fun sesion(){
        val email = sharedPreferences.getString("email",null)
        loginViewModel.sesion(email){ isEnableView ->
            if (isEnableView){
                binding.clContenedor.visibility = View.INVISIBLE
                goToHome()
            }
        }
    }

    //read any change on inputs
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

    //verify is not empty the input
    private fun validateInputs() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()

        binding.btnLogin.isEnabled = email.isNotEmpty() && pass.isNotEmpty()
    }
}