package com.globant.carlosmunoz.myweather.viewmodels

import androidx.lifecycle.*
import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.utils.applyQueries
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import java.lang.NullPointerException


class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    private var weather = MutableLiveData<Result<WeatherResult>>()
    val weatherInfo: MutableLiveData<Result<WeatherResult>>
        get() = weather


    suspend fun reloadWeatherInfo() {
        isLoading.postValue(true)
        weather.postValue(repository.getWeatherInfo(applyQueries()).onEach {
            isLoading.postValue(false)
        }.last())
    }
}

class WeatherVieModelFactory(private val repository: WeatherRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository) as T
        } else {
            throw NullPointerException("ViewModel Class Unknown")
        }
    }
}