package com.example.project.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.databinding.FragmentMainBinding
import com.example.project.view.adapter.PetAdapter
import com.example.project.viewmodel.PetViewModel

class FragmentMain : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val petViewModel: PetViewModel by viewModels()

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

        petViewModel.getListPets()
        petViewModel.listPets.observe(viewLifecycleOwner){ listPets ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = PetAdapter(listPets, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()

        }

    }
//    private fun observerProgress(){
//        petViewModel.progresState.observe(viewLifecycleOwner){status ->
//            binding.progress.isVisible = status
//        }
//    }
}