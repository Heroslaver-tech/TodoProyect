package com.example.project.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project.model.Pet
import com.example.project.utils.Constants.NAME_DB
import com.example.project.data.PetDao

@Database(entities = [Pet::class], version = 1)
abstract class PetDB : RoomDatabase() {

    abstract fun petDao(): PetDao

    companion object{
        fun getDatabase(context: Context): PetDB {
            return Room.databaseBuilder(
                context.applicationContext,
                PetDB::class.java,
                NAME_DB
            ).build()
        }
    }
}