package com.example.project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.ToDo
import com.example.project.repository.ToDoRepository
import kotlinx.coroutines.launch


class ToDoViewModel : ViewModel() {

    private val repository = ToDoRepository()

    private val _toDoList = MutableLiveData<List<ToDo>>()
    val toDoList: LiveData<List<ToDo>> get() = _toDoList

    private val _progressState = MutableLiveData<Boolean>()
    val progressState: LiveData<Boolean> = _progressState

    fun loadToDoList() {
        viewModelScope.launch {
            _toDoList.value = repository.getToDoList()
        }
    }

    fun addToDo(todo: ToDo) {
        viewModelScope.launch {
            _progressState.value = true
            val success = repository.addToDo(todo)
            if (success) {
                loadToDoList()
            }
            _progressState.value = false
        }
    }

    fun deleteToDo(titulo: String) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                repository.deleteToDo(titulo) { error ->
                    if (error == null) {
                        loadToDoList()
                    }
                }
            } catch (e: Exception) {
                // Handle the exception if necessary
            } finally {
                _progressState.value = false
            }
        }
    }

    fun updateToDo(todo: ToDo) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                repository.updateToDo(todo) { error ->
                    if (error == null) {
                        loadToDoList()
                    }
                }
            } catch (e: Exception) {
                // Handle the exception if necessary
            } finally {
                _progressState.value = false
            }
        }
    }

}

