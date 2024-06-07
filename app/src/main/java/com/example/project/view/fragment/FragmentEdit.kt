package com.example.project.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentEditBinding
import com.example.project.model.ToDo
import com.example.project.viewmodel.ToDoViewModel
import java.util.*

class FragmentEdit : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val toDoViewModel: ToDoViewModel by viewModels()
    private lateinit var receivedToDo: ToDo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInventory()
        setupUI()
    }

    private fun setupUI() {
        binding.btnGuardaredicion.setOnClickListener {
            updateInventory()
        }

        binding.toolbarIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun dataInventory() {
        val receivedBundle = arguments
        receivedToDo = receivedBundle?.getSerializable("dataInventory") as ToDo
        binding.editTitulo.setText(receivedToDo.titulo)
        binding.editDescription.setText(receivedToDo.description)
        binding.editEstado.isChecked = receivedToDo.status
        binding.editFecha.setText(receivedToDo.date?.toString() ?: "")
        binding.editPrioridad.setText(receivedToDo.prioridad)
    }

    private fun updateInventory() {
        val titulo = binding.editTitulo.text.toString()
        val description = binding.editDescription.text.toString()
        val status = binding.editEstado.isChecked
        val prioridad = binding.editPrioridad.text.toString()

        // Actualizar el objeto receivedToDo con los nuevos valores
        receivedToDo.titulo = titulo
        receivedToDo.description = description
        receivedToDo.status = status
        receivedToDo.date = Date() // Actualizar la fecha a la actual
        receivedToDo.prioridad = prioridad

        // Llamar a la función de actualización en el ViewModel
        toDoViewModel.updateTODOS(receivedToDo)

        // Navegar de regreso al fragmento principal
        findNavController().popBackStack()
    }
}
