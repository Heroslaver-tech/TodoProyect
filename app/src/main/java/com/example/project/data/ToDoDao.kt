package com.example.project.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.project.model.ToDo

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToDo(inventory: ToDo)

    @Query("SELECT * FROM ToDo")
    suspend fun getListToDo(): MutableList<ToDo>

    @Delete
    suspend fun deleteToDo(inventory: ToDo)

    @Update
    suspend fun updateInventory(inventory: ToDo)
}