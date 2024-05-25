package com.example.project.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.databinding.PetInventoryBinding
import com.example.project.model.Pet

class PetViewHolder(binding: PetInventoryBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController
    fun setPet(pet: Pet) {
        bindingItem.tvName.text = pet.name
        bindingItem.tvSymptom.text = pet.symptom
        bindingItem.tvIndex.text = "#${pet.id.toString()}"

        bindingItem.cvPet.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", pet)
            navController.navigate(R.id.action_fragmentMain_to_fragmentDetails, bundle)
        }

    }
}