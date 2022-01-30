package com.globant.carlosmunoz.myweather.data.api

import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherAPI {
    @GET("/data/2.5/weather")
    suspend fun fetchWeather(@QueryMap queries: Map<String, String>): WeatherResult
}
