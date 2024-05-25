package com.example.project.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.R
import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //fragmentDinamico()

    }

    override fun onBackPressed() {
        // Obtener el ID del fragmento actual
        val currentDestination = findNavController(R.id.navigationContainer).currentDestination?.id

        // Verificar si el fragmento actual es FragmentMain
        if (currentDestination == R.id.fragmentMain) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            // Si el fragmento actual no es FragmentB, dejar que el comportamiento predeterminado del botón de retroceso funcione
            super.onBackPressed()
        }
    }

}