package com.example.project.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.databinding.PetInventoryBinding
import com.example.project.model.ToDo

class ToDoViewHolder(
    private val binding: PetInventoryBinding,
    private val navController: NavController,
    private val onStatusChange: (ToDo, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun setToDo(todo: ToDo) {
        binding.apply {
            tvTitulo.text = todo.titulo
            statusCheckBox.isChecked = todo.status

            statusCheckBox.setOnCheckedChangeListener(null) // Remover el listener para evitar disparar eventos no deseados
            statusCheckBox.isChecked = todo.status // Establecer el estado del CheckBox

            statusCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onStatusChange(todo, isChecked)
            }

            cvPet.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("clave", todo)
                }
                navController.navigate(R.id.action_fragmentMain_to_fragmentDetails, bundle)
            }
        }
    }
}
