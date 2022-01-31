package com.globant.carlosmunoz.myweather.weather

import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.viewmodels.WeatherViewModel
import com.globant.carlosmunoz.myweather.utils.BaseUnitTest
import com.globant.carlosmunoz.myweather.utils.Constants
import com.globant.carlosmunoz.myweather.utils.captureValues
import com.globant.carlosmunoz.myweather.utils.getValueForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.*


class WeatherNetworkViewModelShould : BaseUnitTest() {

    private val repository: WeatherRepository = mock()
    private val weatherResult = mock<WeatherResult>()
    private val expected = Result.success(weatherResult)
    private val mockLongitude = "31.333"

    @ExperimentalCoroutinesApi
    @Test
    fun getCurrentWeatherFromRepository() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.setLatLon(mockLongitude, mockLongitude)
        viewModel.existQueries()
        viewModel.reloadWeatherData()
        viewModel.weatherInfo.getValueForTest()
        verify(repository, times(1)).getWeatherInfo(applyFakeQueries())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun setLoadingToTrueWhenIsFetching() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.reloadWeatherData()
        viewModel.isLoading.captureValues {
            viewModel.weatherInfo.getValueForTest()
            Assert.assertEquals(true, values[0])
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun setLoadingToFalseWhenFetchFinish() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.reloadWeatherData()
        viewModel.setLatLon(mockLongitude, mockLongitude)
        viewModel.existQueries()
        viewModel.reloadWeatherData()
        viewModel.isLoading.captureValues {
            viewModel.weatherInfo.getValueForTest()
            Assert.assertEquals(false, values.last())
        }

    }

    @ExperimentalCoroutinesApi
    private fun mockSuccessCase(): WeatherViewModel {
        runBlockingTest {
            whenever(repository.getWeatherInfo(applyFakeQueries())).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return WeatherViewModel(repository)
    }

    private fun applyFakeQueries(): HashMap<String, String> {
        val queries = hashMapOf<String, String>()
        queries["lat"] = mockLongitude
        queries["lon"] = mockLongitude
        queries["appid"] = Constants.API_KEY
        return queries
    }
}