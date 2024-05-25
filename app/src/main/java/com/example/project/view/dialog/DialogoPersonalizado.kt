package com.example.project.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.project.databinding.DialogoPersonalizadoBinding

class DialogoPersonalizado {
    companion object{
        fun showDialogPersonalizado(
            context: Context
        ) {
            val inflater = LayoutInflater.from(context)
            val binding = DialogoPersonalizadoBinding.inflate(inflater)

            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog.setCancelable(false) //para cuando se cliquee por los lados no se pueda
            alertDialog.setView(binding.root) //establecer la vista de un cuadro de dialogo

            binding.btnAceptar.setOnClickListener {
                Toast.makeText(context,"Aceptar", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }
}