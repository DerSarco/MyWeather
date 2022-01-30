package com.globant.carlosmunoz.myweather.repository

import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.service.WeatherService
import kotlinx.coroutines.flow.Flow


class WeatherRepository(private val service: WeatherService) {
    suspend fun getWeatherInfo(): Flow<Result<List<WeatherResult>>> = service.fetchWeather()
}