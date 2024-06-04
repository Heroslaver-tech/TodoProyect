package com.example.project.repository
import com.example.project.model.ToDo
import com.google.firebase.firestore.FirebaseFirestore

class ToDoRepository(private val database: FirebaseFirestore) {

    fun getToDoList(result: (List<ToDo>?, String?) -> Unit) {
        database.collection("tareas")
            .get()
            .addOnSuccessListener { documents ->
                val todoList = mutableListOf<ToDo>()
                for (document in documents) {
                    val todo = document.toObject(ToDo::class.java)
                    todoList.add(todo)
                }
                result(todoList, null)
            }
            .addOnFailureListener { exception ->
                result(null, exception.localizedMessage)
            }
    }

    fun addToDo(todo: ToDo, result: (String?) -> Unit) {
        database.collection("tareas")
            .add(todo)
            .addOnSuccessListener {
                result(null)
            }
            .addOnFailureListener { exception ->
                result(exception.localizedMessage)
            }
    }

    fun updateToDo(todo: ToDo, result: (String?) -> Unit) {
        val todoId = todo.id
        if (todoId != null) {
            database.collection("tareas").document(todoId)
                .set(todo)
                .addOnSuccessListener {
                    result(null)
                }
                .addOnFailureListener { exception ->
                    result(exception.localizedMessage)
                }
        } else {
            result("ID de tarea nulo")
        }
    }

    fun deleteToDo(todoId: String, result: (String?) -> Unit) {
        database.collection("tareas").document(todoId)
            .delete()
            .addOnSuccessListener {
                result(null)
            }
            .addOnFailureListener { exception ->
                result(exception.localizedMessage)
            }
    }
}










//import com.example.project.model.ToDo
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//
//class ToDoRepository {
//    private val db = FirebaseFirestore.getInstance()
//
//    suspend fun createToDo(todo: ToDo): Result<Unit> {
//        return try {
//            db.collection("tareas").add(todo).await()
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    suspend fun getToDos(): Result<List<ToDo>> {
//        return try {
//            val snapshot = db.collection("tareas").get().await()
//            val todos = snapshot.toObjects(ToDo::class.java)
//            Result.success(todos)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    suspend fun updateToDo(id: String, todo: ToDo): Result<Unit> {
//        return try {
//            db.collection("tareas").document(id).set(todo).await()
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    suspend fun deleteToDo(id: String): Result<Unit> {
//        return try {
//            db.collection("tareas").document(id).delete().await()
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}
//import android.content.Context
//import com.example.project.model.ToDo
//
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//
//class ToDoRepository(val context: Context){
//    private var toDoDao: ToDoDao = ToDoDB.getDatabase(context).toDoDao()
//    //private var apiService: ApiService = ApiUtils.getApiService()
//    suspend fun saveToDo(inventory: ToDo){
//        withContext(Dispatchers.IO){
//            toDoDao.saveToDo(inventory)
//        }
//    }
//
//    suspend fun getListToDos():MutableList<ToDo>{
//        return withContext(Dispatchers.IO){
//            toDoDao.getListToDo()
//        }
//    }
//
//    suspend fun deleteToDo(inventory: ToDo){
//        withContext(Dispatchers.IO){
//            toDoDao.deleteToDo(inventory)
//        }
//    }
//
//    suspend fun updateRepositoy(inventory: ToDo){
//        withContext(Dispatchers.IO){
//            toDoDao.updateInventory(inventory)
//        }
//    }
//
//}
