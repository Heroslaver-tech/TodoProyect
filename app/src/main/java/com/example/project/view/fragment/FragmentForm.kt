package com.example.project.view.fragment


import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project.databinding.FragmentFormBinding
import com.example.project.model.ToDo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Date


class FragmentForm : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val db = FirebaseFirestore.getInstance()
    private val calendar = Calendar.getInstance()
    private var selectedDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()
        listarToDos()
    }

    private fun setup() {
        binding.btnSavePet.setOnClickListener {
            guardarToDo()
        }

        binding.etFecha.setOnClickListener {
            showDatePickerDialog()
        }

    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.etFecha.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }


    private fun guardarToDo() {
        val titulo = binding.etTitulo.text.toString()
        val description = binding.etDescription.text.toString()
        val fecha = Date()
        val status = false
        // Obtener el índice del elemento seleccionado en el Spinner
        val selectedPosition = binding.spPrioridad.selectedItemPosition
        // Obtener el valor del elemento seleccionado utilizando el índice
        val prioridad = binding.spPrioridad.getItemAtPosition(selectedPosition).toString()

        // Verificar que los campos obligatorios no estén vacíos
        if (titulo.isNotEmpty() && description.isNotEmpty() && prioridad.isNotEmpty()) {
            val tarea = ToDo(titulo, description, status , fecha , prioridad )
            // Crear un mapa de datos con los valores a guardar
            db.collection("tarea").document(tarea.titulo).set(
                hashMapOf(

                "titulo" to titulo,
                "description" to description,
                "fecha" to fecha,
                "status" to status,
                "prioridad" to prioridad
                )
            )
            listarToDos()
        }
    }

    private fun listarToDos(){
        db.collection("tarea").get().addOnSuccessListener {
            var data = ""
            for (document in it.documents) {
                // Aquí puedes personalizar cómo deseas mostrar cada artículo en la lista
                data += "titulo: ${document.get("titulo")} " +
                        "description: ${document.get("description")} " +
                        "status: ${document.get("status")}" +
                        "prioridad: ${document.get("prioridad")}\n\n"
                Log.d("listarToDos",data)

            }

        }
    }


}




