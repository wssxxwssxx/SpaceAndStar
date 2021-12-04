package com.example.nasa.service.mars

import com.google.gson.annotations.SerializedName

data class Rover(
    @SerializedName("id")
    val idRover: String,

    @SerializedName("name")
    val nameRover: String,

    @SerializedName("landing_date")
    val landingDateRover: String,

    @SerializedName("launch_date")
    val launchDateRover: String,

    @SerializedName("status")
    val statusRover: String
)