package com.example.project.di

import android.content.Context
import com.example.project.data.PetDB
import com.example.project.data.PetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://66456e7db8925626f891d9a2.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePetDB(@ApplicationContext context: Context):PetDB{
        return PetDB.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideDaoReto(petDB: PetDB): PetDao{
        return petDB.petDao()
    }
}