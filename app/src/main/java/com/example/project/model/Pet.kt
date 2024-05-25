package com.example.project.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Pet(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String,
    val symptom:String,
    val race:String,
    val tel:Long,
    val namePerson: String
): Serializable
