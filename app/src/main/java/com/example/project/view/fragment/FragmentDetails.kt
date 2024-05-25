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
import com.example.project.model.Pet
import com.example.project.viewmodel.PetViewModel

class FragmentDetails : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val petViewModel: PetViewModel by viewModels()
    private lateinit var receivedPet: Pet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // Obtener referencia al ConstraintLayout padre
//        val parentLayout = binding.parentLayout
//
//        // Traer ivPetDetails al frente
//        parentLayout.bringChildToFront(binding.ivPetDetails)

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
            deleteInventory()
        }

        binding.fbEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("dataInventory", receivedPet)
            findNavController().navigate(R.id.action_fragmentDetails_to_fragmentEdit, bundle)
        }
    }

    private fun dataInventory() {
        val receivedBundle = arguments
        receivedPet = receivedBundle?.getSerializable("clave") as Pet
        binding.tvRaceEdit.text = "${receivedPet.race}"
        binding.tvSymptomEdit.text = "${receivedPet.symptom}"
        binding.tvnamePersonEdit.text = "${receivedPet.namePerson}"
        binding.tvTelEdit.text = receivedPet.tel.toString()
        binding.tvIdEdit.text = "#${receivedPet.id}"

    }

    private fun deleteInventory(){
        petViewModel.deletePet(receivedPet)
        petViewModel.getListPets()
        findNavController().popBackStack()
    }

    private fun goBack() {
        binding.toolbarIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}