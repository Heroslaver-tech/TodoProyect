package com.example.project.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val titulo:String,
    val description: String,
    val status: Boolean,
    val fecha: String,
    val prioridad:String,

    ): Serializable
