package com.example.project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.project.model.ToDo
import com.example.project.repository.ToDoRepository
import kotlinx.coroutines.launch
import java.util.Locale


class ToDoViewModel : ViewModel() {

    private val repository = ToDoRepository()

    private val _progressState = MutableLiveData<Boolean>()
    val progressState: LiveData<Boolean> = _progressState

    private val _toDoList = MutableLiveData<List<ToDo>>()
    val toDoList: LiveData<List<ToDo>> get() = _toDoList

    // LiveData para manejar errores
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error
    fun fetchToDoList():LiveData<MutableList<ToDo>>{
        val mutableData = MutableLiveData<MutableList<ToDo>>()
        repository.getListToDos().observeForever { todolist ->
            mutableData.value = todolist
        }
        return mutableData
    }

    fun addToDo(todo: ToDo) {
        viewModelScope.launch {
            _progressState.value = true
            val success = repository.addToDo(todo)
            if (success) {
                fetchToDoList()
            }
            _progressState.value = false
        }
    }

    // Función para obtener la lista de tareas
    fun getToDoList() {
        viewModelScope.launch {
            try {
                val list = repository.getToDoList()
                _toDoList.value = list
                Log.d(TAG, "ViewModel ToDo list: $list") // Añadir este log
                _error.value = null // Resetear el error si la carga fue exitosa
            } catch (e: Exception) {
                _error.value = "Error fetching todos: ${e.message}"
                Log.e(TAG, "Error in ViewModel: ${e.message}", e) // Añadir este log
            }
        }
    }

    fun updateToDoStatus(toDoTitulo: String, isChecked: Boolean) {
        viewModelScope.launch {
            try {
                Log.e(TAG, "toDoTitulo: ${toDoTitulo}")
                repository.updateToDoStatus(toDoTitulo, isChecked)
            } catch (e: Exception) {
                Log.e(TAG, "Error updating ToDo status: ${e.message}", e)
            }
        }
    }

    companion object {
        private const val TAG = "TodoViewModel"
    }



    fun deleteToDo(titulo: String) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                repository.deleteToDo(titulo) { error ->
                    if (error == null) {
                        //loadToDoList()
                        fetchToDoList()
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
                        //loadToDoList()
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



