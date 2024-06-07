package com.example.project.view.fragment


import ToDoAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.databinding.FragmentMainBinding
<<<<<<< Updated upstream

import com.example.project.viewmodel.ToDoViewModel
=======
import com.example.project.view.adapter.PetAdapter
import com.example.project.viewmodel.PetViewModel
import dagger.hilt.android.AndroidEntryPoint
>>>>>>> Stashed changes

@AndroidEntryPoint
class FragmentMain : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var toDoViewModel: ToDoViewModel
    private lateinit var toDoAdapter: ToDoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        toDoViewModel = ViewModelProvider(this).get(ToDoViewModel::class.java)
        toDoAdapter = ToDoAdapter(emptyList(), findNavController(), toDoViewModel)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = toDoViewModel

        setupRecyclerView()
        observeViewModel()
        controladores()

        return binding.root
    }

    private fun controladores() {
        binding.btnagregar.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentForm)
        }
    }
    private fun setupRecyclerView() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = toDoAdapter
        }
    }

    private fun observeViewModel() {
        toDoViewModel.getToDoList()
        toDoViewModel.toDoList.observe(viewLifecycleOwner) { toDoList ->
            Log.d(TAG, "Observed ToDo list: $toDoList") // Añadir este log
            toDoAdapter.updateList(toDoList)
        }
    }

    companion object {
        private const val TAG = "FragmentMain"
    }

}


