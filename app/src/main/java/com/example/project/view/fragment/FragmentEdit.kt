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
import com.example.project.model.Pet
import com.example.project.viewmodel.PetViewModel

class FragmentEdit : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val petViewModel: PetViewModel by viewModels()
    private lateinit var receivedPet: Pet

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
        controladores()
        goBack()
    }

    private fun controladores(){
        binding.btnEdit.setOnClickListener {
            updateInventory()
        }
    }

    private fun dataInventory(){
        val receivedBundle = arguments
        receivedPet = receivedBundle?.getSerializable("dataInventory") as Pet
        binding.etName.setText(receivedPet.name)
        binding.etRace.setText(receivedPet.race)
        binding.etNamePerson.setText(receivedPet.namePerson)
        binding.etTel.setText(receivedPet.tel.toString())

    }

    private fun updateInventory(){
        val name = binding.etName.text.toString()
        val race = binding.etRace.text.toString()
        val namePerson = binding.etNamePerson.text.toString()
        val tel = binding.etTel.text.toString().toLong()
        val inventory = Pet(receivedPet.id, name = name, race = race, namePerson = namePerson, tel = tel, symptom = receivedPet.symptom)
        petViewModel.updatePet(inventory)
        findNavController().navigate(R.id.action_fragmentEdit_to_fragmentMain)

    }

    private fun goBack() {
        binding.toolbarIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}