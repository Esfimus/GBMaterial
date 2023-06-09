package com.example.gbmaterial.data.api

import com.example.gbmaterial.BuildConfig
import com.example.gbmaterial.data.api.apod.Apod
import com.example.gbmaterial.data.api.epic.Epic
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoadNasaApi {

    private val apodApi = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(RetrofitNasaApod::class.java)

    private val epicApi = Retrofit.Builder()
        .baseUrl("https://epic.gsfc.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(RetrofitNasaEpic::class.java)

    fun getApod(date: String, callback: Callback<Apod>) {
        apodApi.getApodData(BuildConfig.API_KEY, date).enqueue(callback)
    }

    fun getEpic(callback: Callback<Epic>) {
        epicApi.getEpicData(BuildConfig.API_KEY).enqueue(callback)
    }
}