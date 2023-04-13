package com.example.gbmaterial.data.api.epic


import com.google.gson.annotations.SerializedName

data class SunJ2000PositionX(
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
    @SerializedName("z")
    val z: Double
)