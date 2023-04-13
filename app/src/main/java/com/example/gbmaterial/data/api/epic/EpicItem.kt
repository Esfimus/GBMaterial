package com.example.gbmaterial.data.api.epic


import com.google.gson.annotations.SerializedName

data class EpicItem(
    @SerializedName("attitude_quaternions")
    val attitudeQuaternions: AttitudeQuaternions,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("centroid_coordinates")
    val centroidCoordinates: CentroidCoordinates,
    @SerializedName("coords")
    val coords: Coords,
    @SerializedName("date")
    val date: String,
    @SerializedName("dscovr_j2000_position")
    val dscovrJ2000Position: DscovrJ2000PositionX,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("lunar_j2000_position")
    val lunarJ2000Position: LunarJ2000PositionX,
    @SerializedName("sun_j2000_position")
    val sunJ2000Position: SunJ2000PositionX,
    @SerializedName("version")
    val version: String
)