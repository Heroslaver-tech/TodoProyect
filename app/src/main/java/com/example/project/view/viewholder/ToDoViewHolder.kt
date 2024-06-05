package com.example.project.view.viewholder


import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.databinding.PetInventoryBinding
import com.example.project.model.ToDo

class ToDoViewHolder(binding: PetInventoryBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController
    fun setToDo(todo: ToDo) {
        bindingItem.tvTitulo.text = todo.titulo
        bindingItem.tvSymptom.text = todo.prioridad


        bindingItem.cvPet.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", todo)
            navController.navigate(R.id.action_fragmentMain_to_fragmentDetails, bundle)
        }
    }
}

