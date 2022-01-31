package com.globant.carlosmunoz.myweather.service

import com.globant.carlosmunoz.myweather.data.api.WeatherAPI
import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException

class WeatherService(
    private var weatherAPI: WeatherAPI
) {

    suspend fun fetchWeather(queries: HashMap<String, String>):
            Flow<Result<WeatherResult>> {
        return flow {
            emit(Result.success(weatherAPI.fetchWeather(queries)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}