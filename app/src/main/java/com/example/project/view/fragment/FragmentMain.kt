package com.example.project.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.databinding.FragmentMainBinding
import com.example.project.view.Adapter.ToDoAdapter
import com.example.project.viewmodel.ToDoViewModel


class FragmentMain : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observadorViewModel()
    }
    //Redirige cuando se presione el boton de agregar
    private fun controladores() {
        binding.btnagregar.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentForm)
        }
    }
    private fun observadorViewModel(){
        observerListInventory()
        //observerProgress()
    }
    private fun observerListInventory(){

        toDoViewModel.loadToDoList()
        toDoViewModel.toDoList.observe(viewLifecycleOwner){toDoList ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = ToDoAdapter(toDoList, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }

}

