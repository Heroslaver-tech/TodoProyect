package com.example.project.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project.model.ToDo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ToDoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val todoCollection = db.collection("tarea")

    fun getListToDos(): LiveData<MutableList<ToDo>> {
        val mutableData = MutableLiveData<MutableList<ToDo>>()
        db.collection("tarea").get().addOnSuccessListener { result ->
            val toDoList = mutableListOf<ToDo>()

            for (document in result) {
                val toDo = document.toObject(ToDo::class.java)
                toDoList.add(toDo)
            }
            mutableData.value = toDoList
        }.addOnFailureListener { exception ->
            // Manejo de errores
        }

        return mutableData
    }

    suspend fun addToDo(todo: ToDo): Boolean {
        return try {
            db.collection("tarea").document(todo.titulo).set(todo).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getToDoList(): MutableList<ToDo> {
        return try {
            Log.d(TAG, "Fetching todos from Firestore...") // Log inicial
            val querySnapshot = todoCollection.get().await()
            Log.d(TAG, "Query snapshot received: ${querySnapshot.documents.size} documents found")

            val todoList = mutableListOf<ToDo>()
            for (document in querySnapshot.documents) {
                Log.d(TAG, "Processing document: ${document.id}")
                val todo = document.toObject(ToDo::class.java)
                if (todo != null) {
                    todoList.add(todo)
                    Log.d(TAG, "Added todo: $todo")
                } else {
                    Log.w(TAG, "Failed to convert document to ToDo: ${document.id}")
                }
            }
            Log.d(TAG, "Fetched todos: $todoList")
            todoList
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching todos: ${e.message}", e)
            mutableListOf() // Retornar una lista mutable vacía en caso de error
        }
    }

    companion object {
        private const val TAG = "ToDoRepository"
    }

    fun updateToDoStatus(toDoTitulo: String, isChecked: Boolean) {
        val docRef = todoCollection.document(toDoTitulo)
        docRef.update("status", isChecked)
            .addOnSuccessListener {
                Log.d(TAG, "ToDo status updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating ToDo status", e)
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


