package com.example.project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project.model.ToDo
import com.example.project.repository.ToDoRepository
import com.google.firebase.firestore.FirebaseFirestore



class ToDoViewModel(private val application: Application) : AndroidViewModel(application) {

    private val toDoRepository = ToDoRepository(FirebaseFirestore.getInstance())
    private val _toDoList = MutableLiveData<List<ToDo>>()
    val toDoList: LiveData<List<ToDo>> = _toDoList

    private val _addToDoResult = MutableLiveData<String?>()
    val addToDoResult: LiveData<String?> = _addToDoResult

    private val _updateToDoResult = MutableLiveData<String?>()
    val updateToDoResult: LiveData<String?> = _updateToDoResult

    private val _deleteToDoResult = MutableLiveData<String?>()
    val deleteToDoResult: LiveData<String?> = _deleteToDoResult

//    fun getToDoList() {
//        toDoRepository.getToDoList { list, message ->
//            if (list != null) {
//                _toDoList.value = list
//            } else {
//                _toDoList.value = listOf()
//            }
//            if (message != null) {
//                _addToDoResult.value = message
//            }
//        }
//    }

    fun addToDo(todo: ToDo) {
        toDoRepository.addToDo(todo) { message ->
            _addToDoResult.value = message
        }
    }

    fun updateToDo(todo: ToDo) {
        toDoRepository.updateToDo(todo) { message ->
            _updateToDoResult.value = message
        }
    }

    fun deleteToDo(todoId: String) {
        toDoRepository.deleteToDo(todoId) { message ->
            _deleteToDoResult.value = message
        }
    }
}
