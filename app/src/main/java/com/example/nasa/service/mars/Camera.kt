package com.example.nasa.service.mars

import com.google.gson.annotations.SerializedName

data class Camera(
    @SerializedName("id")
    private val cameraId: String,

    @SerializedName("name")
    private val nameCamera: String,

    @SerializedName("rover_id")
    private val roverId: String,

    @SerializedName("full_name")
    private val fullName: String
)