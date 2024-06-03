package com.example.project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.example.project.model.ToDo
import com.example.project.repository.ToDoRepository
import kotlinx.coroutines.launch


class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val toDoRepository = ToDoRepository(context)

    private val _listToDos = MutableLiveData<MutableList<ToDo>>()
    val listToDos: LiveData<MutableList<ToDo>> get() = _listToDos

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    fun saveToDo(inventory: ToDo) {
        viewModelScope.launch {
            toDoRepository.saveToDo(inventory)
        }
    }

    fun getListToDos() {
        viewModelScope.launch {

            _listToDos.value = toDoRepository.getListToDos()
        }
    }

    fun deleteToDo(inventory: ToDo) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                toDoRepository.deleteToDo(inventory)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }

        }
    }

    fun updateToDo(inventory: ToDo) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                toDoRepository.updateRepositoy(inventory)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }


}


