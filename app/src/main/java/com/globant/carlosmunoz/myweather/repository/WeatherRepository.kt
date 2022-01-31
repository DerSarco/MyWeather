package com.globant.carlosmunoz.myweather.repository

import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.service.WeatherService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.HashMap


class WeatherRepository(private val service: WeatherService) {
    suspend fun getWeatherInfo(queries: HashMap<String, String>)
            : Flow<Result<WeatherResult>> = service.fetchWeather(queries).map {
        if (it.isSuccess)
            Result.success(it.getOrNull()!!)
        else
            Result.failure(it.exceptionOrNull()!!)
    }
}