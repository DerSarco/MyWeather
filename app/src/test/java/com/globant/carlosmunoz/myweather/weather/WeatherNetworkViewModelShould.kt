package com.globant.carlosmunoz.myweather.weather

import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.viewmodels.WeatherViewModel
import com.globant.carlosmunoz.myweather.utils.BaseUnitTest
import com.globant.carlosmunoz.myweather.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class WeatherNetworkViewModelShould : BaseUnitTest() {

    private val repository: WeatherRepository = mock()
    private val weatherResult = mock<List<WeatherResult>>()
    private val expected = Result.success(weatherResult)

    @Test
    fun getCurrentWeatherFromRepository() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.weather.getValueForTest()
        verify(repository, times(1)).getWeatherInfo()
    }

    private fun mockSuccessCase(): WeatherViewModel {
        runBlockingTest {
            whenever(repository.getWeatherInfo()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return WeatherViewModel(repository)

    }
}