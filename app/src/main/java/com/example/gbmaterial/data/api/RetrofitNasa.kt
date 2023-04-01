package com.example.gbmaterial.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNasa {
    @GET("planetary/apod")
    fun getData(
        @Query("api_key") key: String,
        @Query("date") date: String
    ) : Call<PictureLoaded>
}