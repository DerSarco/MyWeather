package com.globant.carlosmunoz.myweather.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.utils.Constants
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.HashMap


class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private lateinit var timer: Timer
    private var existTimer = MutableLiveData<Boolean>()
    var isLoading = MutableLiveData(false)
    private var latLon = MutableLiveData<HashMap<String, String>>()
    private var weather = MutableLiveData<Result<WeatherResult>>()
    val weatherInfo: MutableLiveData<Result<WeatherResult>>
        get() = weather


    fun reloadWeatherData() = viewModelScope.launch {
        isLoading.postValue(true)
        when (existQueries()) {
            true -> {
                weather.postValue(
                    repository.getWeatherInfo(applyQueries()).onEach {
                        isLoading.postValue(
                            false
                        )
                    }.first()
                )
            }
            false -> isLoading.postValue(true)
        }
    }

    fun existQueries(): Boolean {
        return !latLon.value?.get("lon").isNullOrEmpty() && !latLon.value?.get("lon")
            .isNullOrEmpty()
    }

    fun setLatLon(lat: String, lon: String) {
        val hashMap = hashMapOf(Pair("lat", lat), Pair("lon", lon))
        latLon.postValue(hashMap)
    }

    fun setTimerForRequest() {
        existTimer.postValue(true)
        timer = Timer()
        timer.scheduleAtFixedRate(
            object : java.util.TimerTask() {
                override fun run() {
                    viewModelScope.launch {
                        reloadWeatherData()
                        Log.d("Done", "Reload called!")
                    }
                }
            },
            0, 50000
        )
    }

    fun cancelTimer() {
        Log.d("timer", "$timer.")
        if (existTimer.value!!) {
            timer.cancel()
        }
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries = hashMapOf<String, String>()
        queries["lat"] = latLon.value?.get("lat").toString()
        queries["lon"] = latLon.value?.get("lon").toString()
        queries["appid"] = Constants.API_KEY
        return queries
    }

    override fun onCleared() {
        cancelTimer()
        super.onCleared()
    }

    class WeatherVieModelFactory(private val repository: WeatherRepository) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
                return WeatherViewModel(repository) as T
            } else {
                throw NullPointerException("ViewModel Class Unknown")
            }
        }
    }
}

