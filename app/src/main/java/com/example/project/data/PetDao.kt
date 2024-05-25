package com.example.project.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.project.model.Pet

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePet(inventory: Pet)

    @Query("SELECT * FROM Pet")
    suspend fun getListPets(): MutableList<Pet>

    @Delete
    suspend fun deletePet(inventory: Pet)

    @Update
    suspend fun updateInventory(inventory: Pet)
}