package com.example.project.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project.repository.LoginRepository

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    //Comunicate with repository to register
    fun registerUser(email: String, pass: String, isRegister: (Boolean) -> Unit) {
        repository.registerUser(email, pass) { response ->
                isRegister(response)
        }
    }

    //Comunicate with repository to login
    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {

        repository.loginUser(email, pass) { response ->
                isLogin(response)
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}