package com.globant.carlosmunoz.myweather.service

import com.globant.carlosmunoz.myweather.data.api.WeatherAPI
import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherService(
    private val weatherAPI: WeatherAPI
) {
     suspend fun fetchWeather():Flow<Result<List<WeatherResult>>> {
         return flow {
             emit(Result.success(weatherAPI.fetchWeather()))
         }
     }
}