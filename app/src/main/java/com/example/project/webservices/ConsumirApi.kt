package com.example.project.webservices

import retrofit2.Call
import retrofit2.http.GET

interface ConsumirApi {
    @GET("dogs")
    fun getTraer(): Call<List<Raza>>
}