package com.example.project.view.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.databinding.FragmentFormBinding
import com.example.project.model.ToDo
import com.example.project.viewmodel.ToDoViewModel
import java.util.Calendar
import java.util.Date
import com.example.project.R


class FragmentForm : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val calendar = Calendar.getInstance()
    private var selectedDate: String? = null
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()


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
            { _, year, monthOfYear, dayOfMonth ->
                selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.etFecha.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }


//    private fun goBack() {
//        binding.toolbarIcon.setOnClickListener {
//            findNavController().popBackStack()
//        }
//    }

    private fun guardarToDo() {
        val titulo = binding.etTitulo.text.toString()
        val description = binding.etDescription.text.toString()
        val fecha = Date()
        val status = false
        val selectedPosition = binding.spPrioridad.selectedItemPosition
        val prioridad = binding.spPrioridad.getItemAtPosition(selectedPosition).toString()

        if (titulo.isNotEmpty() && description.isNotEmpty() && prioridad.isNotEmpty()) {
            val tarea = ToDo(titulo = titulo, description = description, status = status, date = fecha, prioridad = prioridad)
            toDoViewModel.addToDo(tarea)
            Toast.makeText(requireContext(), "Tarea guardada", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_fragmentForm_to_fragmentMain)

        } else {
            Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}

