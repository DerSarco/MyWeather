package com.globant.carlosmunoz.myweather.data.api

import com.globant.carlosmunoz.myweather.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherNetwork {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRetrofit(): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
    }

}