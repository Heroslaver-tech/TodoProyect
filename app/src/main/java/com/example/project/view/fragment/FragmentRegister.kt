package com.example.project.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentRegisterBinding
import com.example.project.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task

class FragmentRegister : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var googleSignInClient: GoogleSignInClient

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setupGoogleSignIn() // Configuración de Google Sign-In
        setup()
        setupTextWatchers()
        return binding.root
    }

    // Configura el inicio de sesión con Google
    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        binding.btnGoogle.setSize(SignInButton.SIZE_WIDE)
        setGooglePlusButtonText(binding.btnGoogle)

        binding.btnGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    // Cambia el texto del botón de Google Sign-In
    private fun setGooglePlusButtonText(signInButton: SignInButton) {
        for (i in 0 until signInButton.childCount) {
            val v = signInButton.getChildAt(i)
            if (v is Button) {
                v.text = "Sign up with Google"
                return
            }
        }
    }

    // Inicia el proceso de inicio de sesión con Google
    private fun signInWithGoogle() {
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }
    }

    // Maneja el resultado del intento de inicio de sesión con Google
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(Exception::class.java)
            if (account != null) {
                // Maneja el registro exitoso aquí
                Toast.makeText(context, "Google Sign-In successful", Toast.LENGTH_SHORT).show()
                goToLogin()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
        }
    }

    // Configura los listeners para los botones
    private fun setup() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        // Inicializa el botón en deshabilitado
        binding.btnRegister.isEnabled = false
    }

    // Navega a la vista de inicio de sesión
    private fun goToLogin(){
        findNavController().navigate(R.id.action_fragmentRegister_to_fragmentLogin)
    }

    // Registra al usuario
    private fun registerUser(){
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()

        // Verifica si el email y la contraseña no están vacíos
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(context, "Please complete all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica si la contraseña tiene al menos 6 caracteres
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

    // Configura los listeners para los cambios en los campos de texto
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

    // Valida que los campos de email y contraseña no estén vacíos
    private fun validateInputs() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()

        binding.btnRegister.isEnabled = email.isNotEmpty() && pass.isNotEmpty() && pass.length >= 6
    }
}
