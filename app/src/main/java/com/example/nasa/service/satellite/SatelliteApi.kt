package com.example.nasa.service.satellite

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

//https://tle.ivanstanojevic.me/api/tle/{id}/propagate
interface SatelliteApi {

    @GET("api/tle/{id}/propagate")
    fun getSattellitePosition(
        @Path("id") idSatellite: String
    )
    :Observable<SattelliteVO>

    companion object{
        val BASE_URL = "https://tle.ivanstanojevic.me/"
    }
}