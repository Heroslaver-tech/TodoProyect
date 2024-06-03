package com.example.project.repository
import android.content.Context
import com.example.project.data.ToDoDB
import com.example.project.data.ToDoDao
import com.example.project.model.ToDo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ToDoRepository(val context: Context){
    private var toDoDao: ToDoDao = ToDoDB.getDatabase(context).toDoDao()
    //private var apiService: ApiService = ApiUtils.getApiService()
    suspend fun saveToDo(inventory: ToDo){
        withContext(Dispatchers.IO){
            toDoDao.saveToDo(inventory)
        }
    }

    suspend fun getListToDos():MutableList<ToDo>{
        return withContext(Dispatchers.IO){
            toDoDao.getListToDo()
        }
    }

    suspend fun deleteToDo(inventory: ToDo){
        withContext(Dispatchers.IO){
            toDoDao.deleteToDo(inventory)
        }
    }

    suspend fun updateRepositoy(inventory: ToDo){
        withContext(Dispatchers.IO){
            toDoDao.updateInventory(inventory)
        }
    }

}
