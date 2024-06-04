package com.example.project.repository

import com.example.project.model.ToDo
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Date
import kotlinx.coroutines.tasks.await

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ToDoRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getToDoList(): List<ToDo> {
        return try {
            val documents = db.collection("tarea").get().await()
            documents.map { document ->
                document.toObject(ToDo::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }


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
            .addOnSuccessListener {
                result(null)
            }
            .addOnFailureListener { exception ->
                result(exception.localizedMessage)
            }
    }

    fun updateToDo(todo: ToDo, result: (String?) -> Unit) {
        db.collection("tarea")
            .document(todo.titulo)
            .set(todo)
            .addOnSuccessListener {
                result(null)
            }
            .addOnFailureListener { exception ->
                result(exception.localizedMessage)
            }
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
//    suspend fun saveToDo(titulo: String, description: String, status: Boolean, fecha: Date, prioridad: String) {
//        return suspendCoroutine { continuation ->
//            db.collection("tarea").document(titulo).set(
//                hashMapOf(
//                    "titulo" to titulo,
//                    "description" to description,
//                    "fecha" to fecha,
//                    "status" to status,
//                    "prioridad" to prioridad
//                )
//            )
//                .addOnSuccessListener {
//                    continuation.resume(Unit)
//                }
//                .addOnFailureListener { exception ->
//                    continuation.resumeWithException(exception)
//                }
//        }
//    }
//}



