package com.example.gbmaterial.data.api.epic


import com.google.gson.annotations.SerializedName

data class AttitudeQuaternions(
    @SerializedName("q0")
    val q0: Double,
    @SerializedName("q1")
    val q1: Double,
    @SerializedName("q2")
    val q2: Double,
    @SerializedName("q3")
    val q3: Double
)