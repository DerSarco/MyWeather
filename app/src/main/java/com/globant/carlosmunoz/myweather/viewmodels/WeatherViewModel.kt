package com.globant.carlosmunoz.myweather.viewmodels

import androidx.lifecycle.*
import com.globant.carlosmunoz.myweather.repository.WeatherRepository


class WeatherViewModel(
    repository: WeatherRepository
): ViewModel() {

    val weather = liveData {
        emitSource(repository.getWeatherInfo().asLiveData())
    }

}
