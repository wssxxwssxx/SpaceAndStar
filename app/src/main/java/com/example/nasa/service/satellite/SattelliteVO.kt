package com.example.nasa.service.satellite

import com.google.gson.annotations.SerializedName

data class SattelliteVO(
    @SerializedName("geodetic")
    val geodetic: Geodetic,

    @SerializedName("tle")
    val tle: Tle
)

class Tle{
    @SerializedName("name")
    val name: String = ""
}

class Geodetic{
    @SerializedName("latitude")
    val latitude: Double = 0.0

    @SerializedName("longitude")
    val longitude: Double = 0.0

}
