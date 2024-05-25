package com.example.project.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentLoginBinding
import java.util.concurrent.Executor

class FragmentLogin : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    //lateinit var binding: ActivityMainBinding
    lateinit var info: String

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.imagFinger.setOnClickListener {
            //findNavController().navigate(R.id.action_fragmentLogin_to_fragmentMain)
            checkDeviceHasBiometric()
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //navigationStandar()
        getbiometricSupport()

    }

    fun getbiometricSupport(){
        //executor = ContextCompat.getMainExecutor(this)
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    userNotify("Authentication error: $errString")
                    //Toast.makeText(FragmentA, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    userNotify("Authentication successful!")
                    //Toast.makeText(this@FragmentA,"Authentication successful!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_fragmentLogin_to_fragmentMain)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    userNotify("Authentication fallò")
                    //Toast.makeText(this@FragmentA,"Authentication failed", Toast.LENGTH_LONG).show()
                }
            })


        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticacion con Biometria")
            .setSubtitle("ingrese su huella digital aqui")
            .setNegativeButtonText("Cancelar")
            .build()


        binding.imagFinger.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun navigationFragmentB(){
        /*binding.btnNavFragmentB.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentA_to_fragmentB)
        }*/
    }

    //validar
    val mStartForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Revisa requestCode
            val data = result.data
            // Realiza alguna tarea.
        }
    }

    fun checkDeviceHasBiometric(){
        //val biometricManager = BiometricManager.from(this)
        val biometricManager = BiometricManager.from(requireContext())
        when(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)){
            BiometricManager.BIOMETRIC_SUCCESS->{
                Log.d("mi app","app can authenticate using biometric")
                info = "App can authenticate using biometrics."
                binding.imagFinger.isEnabled=true

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                Log.d("mi app","No biometric features available on this device.")
                info = "No biometric features available on this device."
                binding.imagFinger.isEnabled=false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                info = "Biometric features are currently unavailable."
                binding.imagFinger.isEnabled = false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                }
                binding.imagFinger.isEnabled=false

                //startActivityForResult(enrollIntent, 100)
                val intent = Intent(requireContext(), FragmentLogin::class.java)
                mStartForResult.launch(intent)

            }
        }
        binding.tmsg.text= info
    }

    fun userNotify(message: String){
        Toast.makeText(context, message,Toast.LENGTH_LONG).show()
    }


}