package com.globant.carlosmunoz.myweather.data.api

import com.globant.carlosmunoz.myweather.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WeatherNetwork {

    private val okHttp = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRetrofit(): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
    }

}