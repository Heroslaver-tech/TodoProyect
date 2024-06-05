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
        binding.btnDelete.setOnClickListener {
            //deleteInventory()
        }

        binding.fbEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("dataInventory", receivedToDo)
            findNavController().navigate(R.id.action_fragmentDetails_to_fragmentEdit, bundle)
        }
    }

    private fun dataInventory() {
        val receivedBundle = arguments
        receivedToDo = receivedBundle?.getSerializable("clave") as ToDo
        binding.tvRaceEdit.text = receivedToDo.titulo

    }

//        private fun deleteInventory(){
//            toDoViewModel.deleteToDo(receivedToDo.toString())
//            toDoViewModel.loadToDoList()
//            findNavController().popBackStack()
//        }

        private fun goBack() {
            binding.toolbarIcon.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }