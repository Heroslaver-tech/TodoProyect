package com.example.project.model

import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.Date

data class ToDo(

    val titulo: String,
    val description: String,
    val status: Boolean,
    @ServerTimestamp
    val date: Date = Date(),
    val prioridad: String,
): Serializable