package com.example.nasa.service.apod

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel

data class ApodVO(

    @SerializedName("date")
    val date:String,

    @SerializedName("hdurl")
    val hdurl: String,

    @SerializedName("explanation")
    val explanation: String,

    @SerializedName("title")
    val title: String,

    var isClick: Boolean = false
)