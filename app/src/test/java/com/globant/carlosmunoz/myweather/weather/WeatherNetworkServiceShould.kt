package com.globant.carlosmunoz.myweather.weather

import com.globant.carlosmunoz.myweather.data.api.WeatherAPI
import com.globant.carlosmunoz.myweather.service.WeatherService
import com.globant.carlosmunoz.myweather.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class WeatherNetworkServiceShould: BaseUnitTest() {

    val weatherApi: WeatherAPI = mock()

    @Test
    fun callApiFetchWeather()= runBlockingTest {
        val service = WeatherService(weatherApi)
        service.fetchWeather().first()
        verify(weatherApi, times(1)).fetchWeather()
    }

}