package com.example.project.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date


data class ToDo(

    var id: String = "",
    val titulo: String,
    val description: String,
    val status: Boolean,
    @ServerTimestamp
    val date: Date,
    val prioridad: String,
)