package com.example.nasa.service.mars

import com.google.gson.annotations.SerializedName


data class PhotosMarsListVO (
    @SerializedName("photos")
    val photosMarsList: ArrayList<PhotoMars>
    )

data class PhotoMars(
    @SerializedName("img_src")
    val imgSource: String,
)