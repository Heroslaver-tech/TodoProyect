package com.example.project.repository
import android.content.Context
import com.example.project.data.PetDB
import com.example.project.data.PetDao
import com.example.project.model.Pet

//import com.example.project.webservice.ApiService
//import com.example.project.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PetRepository(val context: Context){
    private var petDao:PetDao = PetDB.getDatabase(context).petDao()
    //private var apiService: ApiService = ApiUtils.getApiService()
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