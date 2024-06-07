package com.example.project.repository
import android.content.Context
import com.example.project.data.PetDB
import com.example.project.data.PetDao
import com.example.project.model.Pet

//import com.example.project.webservice.ApiService
//import com.example.project.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val petDao: PetDao
){

    suspend fun savePet(inventory:Pet){
        withContext(Dispatchers.IO){
            petDao.savePet(inventory)
        }
    }

    suspend fun getListPets():MutableList<Pet>{
        return withContext(Dispatchers.IO){
            petDao.getListPets()
        }
    }

    suspend fun deletePet(inventory: Pet){
        withContext(Dispatchers.IO){
            petDao.deletePet(inventory)
        }
    }

    suspend fun updateRepositoy(inventory: Pet){
        withContext(Dispatchers.IO){
            petDao.updateInventory(inventory)
        }
    }
}