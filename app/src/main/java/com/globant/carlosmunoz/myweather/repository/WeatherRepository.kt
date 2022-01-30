package com.globant.carlosmunoz.myweather.repository

import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.service.WeatherService
import kotlinx.coroutines.flow.Flow
import java.util.HashMap


class WeatherRepository(private val service: WeatherService) {
    suspend fun getWeatherInfo(queries: HashMap<String, String>)
            : Flow<Result<WeatherResult>> = service.fetchWeather(queries)
}