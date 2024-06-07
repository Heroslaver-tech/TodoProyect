package com.example.project.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentDetailsBinding
import com.example.project.model.ToDo
import com.example.project.viewmodel.ToDoViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class FragmentDetails : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val toDoViewModel: ToDoViewModel by viewModels()
    private lateinit var receivedToDo: ToDo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInventory()
        controladores()
        goBack()
    }

    private fun controladores() {
        binding.btnEliminar.setOnClickListener {
            deleteInventory()
        }

        binding.btnEditar.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("dataInventory", receivedToDo)
            findNavController().navigate(R.id.action_fragmentDetails_to_fragmentEdit, bundle)
        }
    }

    private fun dataInventory() {
        val receivedBundle = arguments
        receivedToDo = receivedBundle?.getSerializable("clave") as ToDo
        binding.tvTituloMos.text = receivedToDo.titulo
        binding.tvDescriptionMost.text = receivedToDo.description
        binding.tvEstadoMost.text = if (receivedToDo.status) "Completado" else "Pendiente"
        binding.tvFechaMos.text = receivedToDo.date.toString() // Ajusta el formato de la fecha según sea necesario
        binding.tvPrioridad.text =receivedToDo.prioridad
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(receivedToDo.date)
        binding.tvFechaMos.text = formattedDate


    }

    private fun deleteInventory() {
        toDoViewModel.deleteToDo(receivedToDo)
        findNavController().popBackStack()
    }

    private fun goBack() {
        binding.toolbarIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}