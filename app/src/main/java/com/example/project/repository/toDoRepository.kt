package com.example.project.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project.model.ToDo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ToDoRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getToDos(): Result<List<ToDo>> {
        return try {
            val snapshot = db.collection("tareas").get().await()
            val todos = snapshot.toObjects(ToDo::class.java)
            Result.success(todos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

//    fun getToDos():LiveData<MutableList<ToDo>> {
//        val mutableData = MutableLiveData<MutableList<ToDo>>()
//        mutableData.value = iterator {  }
//
//    }



    suspend fun addToDo(todo: ToDo): Boolean {
        return try {
            db.collection("tarea").document(todo.titulo).set(todo).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun deleteToDo(titulo: String, result: (String?) -> Unit) {
        db.collection("tarea")
            .document(titulo)
            .delete()
            .addOnSuccessListener { result(null) }
            .addOnFailureListener { exception -> result(exception.localizedMessage) }
    }

    fun updateToDo(todo: ToDo, result: (String?) -> Unit) {
        db.collection("tarea")
            .document(todo.titulo)
            .set(todo)
            .addOnSuccessListener { result(null) }
            .addOnFailureListener { exception -> result(exception.localizedMessage) }
    }


}












//    suspend fun getToDos(): List<ToDo> {
//        return suspendCoroutine { continuation ->
//            db.collection("tarea")
//                .get()
//                .addOnSuccessListener { querySnapshot ->
//                    val toDos = mutableListOf<ToDo>()
//                    for (document in querySnapshot.documents) {
//                        val titulo = document.get("titulo") as String
//                        val description = document.get("description") as String
//                        val status = document.get("status") as Boolean
//                        val fecha = (document.get("fecha") as Timestamp).toDate()
//                        val prioridad = document.get("prioridad") as String
//                        val toDo = ToDo(titulo, description, status, fecha, prioridad)
//                        toDos.add(toDo)
//                    }
//                    continuation.resume(toDos)
//                }
//                .addOnFailureListener { exception ->
//                    continuation.resumeWithException(exception)
//                }
//        }
//    }
//


