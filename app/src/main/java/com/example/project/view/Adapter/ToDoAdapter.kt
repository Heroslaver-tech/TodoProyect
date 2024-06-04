package com.example.project.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.PetInventoryBinding
import com.example.project.model.ToDo
import com.example.project.view.viewholder.ToDoViewHolder

class ToDoAdapter(private val listToDo: List<ToDo>, private val navController: NavController): RecyclerView.Adapter<ToDoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = PetInventoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding, navController)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val toDo = listToDo[position]
        holder.setToDo(toDo)
    }

    override fun getItemCount(): Int {
        return listToDo.size
    }
}