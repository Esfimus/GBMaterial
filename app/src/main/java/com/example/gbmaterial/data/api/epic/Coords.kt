package com.example.gbmaterial.data.api.epic


import com.google.gson.annotations.SerializedName

data class Coords(
    @SerializedName("attitude_quaternions")
    val attitudeQuaternions: AttitudeQuaternions,
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates,
    @SerializedName("dscovr_j2000_position")
    val dscovrJ2000Position: DscovrJ2000PositionX,
    @SerializedName("lunar_j2000_position")
    val lunarJ2000Position: LunarJ2000PositionX,
    @SerializedName("sun_j2000_position")
    val sunJ2000Position: SunJ2000PositionX
)