package com.example.project.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.PetInventoryBinding
import com.example.project.model.Pet
import com.example.project.view.viewholder.PetViewHolder

class PetAdapter(private val listPets:MutableList<Pet>, private val navController: NavController):RecyclerView.Adapter<PetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = PetInventoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return PetViewHolder(binding, navController)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val inventory = listPets[position]
        holder.setPet(inventory)
    }

    override fun getItemCount(): Int {
        return listPets.size
    }
}