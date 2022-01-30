package com.globant.carlosmunoz.myweather.weather

import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.service.WeatherService
import com.globant.carlosmunoz.myweather.utils.BaseUnitTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class WeatherNetworkRepositoryShould: BaseUnitTest() {
    private val service: WeatherService = mock()

    @Test
    fun getWeatherFromService() = runBlockingTest {
        val repository = WeatherRepository(service)
        repository.getWeatherInfo()
        verify(service, times(1)).fetchWeather()
    }

}