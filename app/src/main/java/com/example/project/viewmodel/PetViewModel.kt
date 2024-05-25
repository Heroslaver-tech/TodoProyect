package com.example.project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.project.model.Pet
import com.example.project.repository.PetRepository
import kotlinx.coroutines.launch


class PetViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val petRepository = PetRepository(context)


    private val _listPets = MutableLiveData<MutableList<Pet>>()
    val listPets: LiveData<MutableList<Pet>> get() = _listPets

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    fun savePet(inventory: Pet) {
        viewModelScope.launch {
                petRepository.savePet(inventory)
        }
    }

    fun getListPets() {
        viewModelScope.launch {

            _listPets.value = petRepository.getListPets()
        }
    }

    fun deletePet(inventory: Pet) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                petRepository.deletePet(inventory)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }

        }
    }

    fun updatePet(inventory: Pet) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                petRepository.updateRepositoy(inventory)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

}