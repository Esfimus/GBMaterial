package com.example.gbmaterial.data.api.epic


import com.google.gson.annotations.SerializedName

data class CentroidCoordinates(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)