package com.globant.carlosmunoz.myweather.data.api

import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import retrofit2.http.GET

interface WeatherAPI {

    @GET
    suspend fun fetchWeather(): List<WeatherResult>
}