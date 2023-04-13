package com.example.gbmaterial.data.api

import com.example.gbmaterial.data.api.epic.Epic
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNasaEpic {
    @GET("api/natural")
    fun getEpicData(
        @Query("api_key") key: String
    ) : Call<Epic>
}