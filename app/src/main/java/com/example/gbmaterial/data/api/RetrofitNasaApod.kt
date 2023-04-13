package com.example.gbmaterial.data.api

import com.example.gbmaterial.data.api.apod.Apod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNasaApod {
    @GET("planetary/apod")
    fun getApodData(
        @Query("api_key") key: String,
        @Query("date") date: String
    ) : Call<Apod>
}