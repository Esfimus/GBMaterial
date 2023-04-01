package com.example.gbmaterial.data.api

import com.example.gbmaterial.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoadRetrofitPicture {

    private val pictureApi = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(RetrofitNasa::class.java)

    fun getPicture(date: String, callback: Callback<PictureLoaded>) {
        pictureApi.getData(BuildConfig.API_KEY, date).enqueue(callback)
    }
}