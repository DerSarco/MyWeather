package com.globant.carlosmunoz.myweather.weather

import com.globant.carlosmunoz.myweather.data.entities.WeatherResult
import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.utils.*
import com.globant.carlosmunoz.myweather.viewmodels.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.*


class WeatherNetworkViewModelShould : BaseUnitTest() {

    private val repository: WeatherRepository = mock()
    private val weatherResult = mock<WeatherResult>()
    private val expected = Result.success(weatherResult)
    private val mockLongitude = "31.333"
    private val mockMetric = "standard"


    @ExperimentalCoroutinesApi
    @Test
    fun getCurrentWeatherFromRepository() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.reloadWeatherData()
        viewModel.weatherInfo.getValueForTest()
        verify(repository, times(1)).getWeatherInfo(applyFakeQueries())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun setLoadingToTrueWhenIsFetching() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.isLoading.captureValues {
            viewModel.reloadWeatherData()
            viewModel.weatherInfo.getValueForTest()
            Assert.assertEquals(true, values[0])
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun setLoadingToFalseWhenFetchFinish() = runBlockingTest {
        val viewModel = mockSuccessCase()
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

        val viewModel = WeatherViewModel(repository)
        viewModel.setLatLon(mockLongitude, mockLongitude)
        viewModel.setMeasureUnit(mockMetric)
        return viewModel
    }

    private fun applyFakeQueries(): HashMap<String, String> {
        val queries = hashMapOf<String, String>()
        queries["lat"] = mockLongitude
        queries["lon"] = mockLongitude
        queries["units"] = mockMetric
        queries["appid"] = Constants.API_KEY
        return queries
    }
}