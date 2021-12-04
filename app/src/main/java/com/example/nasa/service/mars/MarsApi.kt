package com.example.nasa.service.mars

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://api.nasa.gov/planetary/apod?count=10&api_key=EokjtqGQruggybwmxwsS8g5jQrmisUXv7OCROBo8
interface MarsApi {

    @GET("{roversName}/photos")
    fun  getPhotosBySol(
        @Path("roversName") roversName: String,
        @Query("sol") solNumber: String
    ) : Observable<PhotosMarsListVO>

    companion object {
        const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"
        const val BASE_API = "EokjtqGQruggybwmxwsS8g5jQrmisUXv7OCROBo8"
    }

}