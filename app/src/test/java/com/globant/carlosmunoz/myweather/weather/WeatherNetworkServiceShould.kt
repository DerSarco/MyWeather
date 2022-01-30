package com.globant.carlosmunoz.myweather.weather

import com.globant.carlosmunoz.myweather.data.api.WeatherAPI
import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.service.WeatherService
import com.globant.carlosmunoz.myweather.utils.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class WeatherNetworkServiceShould : BaseUnitTest() {

    private val weatherApi: WeatherAPI = mock()
    private val weather = mock<WeatherResult>()
    private val hashMap = mock<HashMap<String,String>>()


    @ExperimentalCoroutinesApi
    @Test
    fun callApiFetchWeather() = runBlockingTest {
        whenever(weatherApi.fetchWeather(hashMap)).thenReturn(
            weather
        )
        val service = WeatherService(weatherApi)
        service.fetchWeather(hashMap).first()
        verify(weatherApi, times(1)).fetchWeather(hashMap)
    }

}