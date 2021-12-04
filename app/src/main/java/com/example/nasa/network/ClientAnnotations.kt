package com.example.nasa.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MarsRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApodRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SatelliteRetrofitCLient