package com.globant.carlosmunoz.myweather.weather

import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.viewmodels.WeatherViewModel
import com.globant.carlosmunoz.myweather.utils.BaseUnitTest
import com.globant.carlosmunoz.myweather.utils.applyQueries
import com.globant.carlosmunoz.myweather.utils.captureValues
import com.globant.carlosmunoz.myweather.utils.getValueForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class WeatherNetworkViewModelShould : BaseUnitTest() {

    private val repository: WeatherRepository = mock()
    private val weatherResult = mock<WeatherResult>()
    private val expected = Result.success(weatherResult)

    @ExperimentalCoroutinesApi
    @Test
    fun getCurrentWeatherFromRepository() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.reloadWeatherInfo()
        viewModel.weatherInfo.getValueForTest()
        verify(repository, times(1)).getWeatherInfo(applyQueries())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun setLoadingToTrueWhenIsFetching() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.isLoading.captureValues {
            viewModel.reloadWeatherInfo()
            Assert.assertEquals(true, values[0])
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun setLoadingToFalseWhenFetchFinish() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.reloadWeatherInfo()
        viewModel.isLoading.captureValues {
            viewModel.weatherInfo.getValueForTest()
            Assert.assertEquals(false, values.last())
        }

    }


    @ExperimentalCoroutinesApi
    private fun mockSuccessCase(): WeatherViewModel {
        runBlockingTest {
            whenever(repository.getWeatherInfo(applyQueries())).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return WeatherViewModel(repository)
    }
}