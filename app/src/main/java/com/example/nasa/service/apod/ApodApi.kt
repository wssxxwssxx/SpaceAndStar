package com.example.nasa.service.apod

import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {

    @GET("planetary/apod/")
    fun getApod(
        @Query("count") count: String
    ): Flowable<Response<List<ApodVO>>>

    companion object{
        const val BASE_URL = "https://api.nasa.gov/"
        const val BASE_API = "EokjtqGQruggybwmxwsS8g5jQrmisUXv7OCROBo8"
    }

}