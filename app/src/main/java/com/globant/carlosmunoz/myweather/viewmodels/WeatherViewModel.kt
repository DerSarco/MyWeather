package com.globant.carlosmunoz.myweather.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.HashMap


class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private var timer = Timer()

    var isLoading = MutableLiveData<Boolean>()
    private var latitude = MutableLiveData<String>()
    val latitudeUser: MutableLiveData<String>
        get() = latitude
    private var longitude = MutableLiveData<String>()
    val longitudeUser: MutableLiveData<String>
        get() = longitude
    private var weather = MutableLiveData<Result<WeatherResult>>()
    val weatherInfo: MutableLiveData<Result<WeatherResult>>
        get() = weather


    suspend fun reloadWeatherData() {
        isLoading.postValue(true)
        weather.postValue(repository.getWeatherInfo(applyQueries()).onEach {
            isLoading.postValue(false)
        }.last())
    }

    fun setLongitude(lon: String) {
        longitude.postValue(lon)
    }

    fun setLatitude(lat: String) {
        latitude.postValue(lat)
    }

    fun setTimerForRequest() {
        timer.scheduleAtFixedRate(
            object : java.util.TimerTask() {
                override fun run() {
                    viewModelScope.launch {
                        reloadWeatherData()
                        Log.d("Done", "Reload called!")
                    }
                }
            },
            0, 10000
        ) // 1000 Millisecond  = 1 second

    }

    fun cancelTimer() {
        timer.cancel()
    }


    private fun applyQueries(): HashMap<String, String> {
        val queries = hashMapOf<String, String>()
        queries["lat"] = latitude.value.toString()
        queries["lon"] = longitude.value.toString()
        queries["appid"] = Constants.API_KEY
        return queries
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