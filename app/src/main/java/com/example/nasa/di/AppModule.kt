package com.example.nasa.di

import com.example.nasa.network.ApodRetrofitClient
import com.example.nasa.network.MarsRetrofitClient
import com.example.nasa.network.SatelliteRetrofitCLient
import com.example.nasa.service.DefaultValueInterceptor
import com.example.nasa.service.apod.ApodApi
import com.example.nasa.service.mars.MarsApi
import com.example.nasa.service.satellite.SatelliteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Mars
    @Provides
    @Singleton
    fun providesMarsApi(@MarsRetrofitClient retrofit: Retrofit): MarsApi =
        retrofit.create(MarsApi::class.java)

    @Provides
    @Singleton
    @MarsRetrofitClient
    fun providesRetrofitMars(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(MarsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun okHttpLoggerProvider(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun okHttpClientMars(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(DefaultValueInterceptor(MarsApi.BASE_API))
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()


    //Apod
    @Singleton
    @Provides
    fun provideApodApi(@ApodRetrofitClient retrofit: Retrofit): ApodApi =
        retrofit.create(ApodApi::class.java)

    @Provides
    @Singleton
    @ApodRetrofitClient
    fun okHttpLoggerProviderApod(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    @ApodRetrofitClient
    fun okHttpClienApod(@ApodRetrofitClient httpLoggingsInterceptor: HttpLoggingInterceptor):OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(DefaultValueInterceptor(ApodApi.BASE_API))
            .addInterceptor(httpLoggingsInterceptor)
            .build()

    @Provides
    @Singleton
    @ApodRetrofitClient
    fun retrofitApodClient(@ApodRetrofitClient httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(ApodApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    //Satellite

    @Provides
    @Singleton
    fun provideSatelliteApi(@SatelliteRetrofitCLient retrofit: Retrofit): SatelliteApi =
        retrofit.create(SatelliteApi::class.java)

    @Provides
    @Singleton
    @SatelliteRetrofitCLient
    fun okHttpLoggerProviderSatellite() : HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    @SatelliteRetrofitCLient
    fun okHttpClientSatellite(@SatelliteRetrofitCLient
                              httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    @SatelliteRetrofitCLient
    fun providesRetrofitClient(@SatelliteRetrofitCLient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(SatelliteApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}