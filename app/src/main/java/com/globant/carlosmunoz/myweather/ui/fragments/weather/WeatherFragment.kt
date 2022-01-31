package com.globant.carlosmunoz.myweather.ui.fragments.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globant.carlosmunoz.myweather.R
import com.globant.carlosmunoz.myweather.data.api.WeatherNetwork
import com.globant.carlosmunoz.myweather.databinding.FragmentWeatherBinding
import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.service.WeatherService
import com.globant.carlosmunoz.myweather.userPrefs
import com.globant.carlosmunoz.myweather.viewmodels.WeatherViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherFragment : Fragment() {

    private lateinit var mBinding: FragmentWeatherBinding
    private lateinit var mViewModel: WeatherViewModel
    private val api = WeatherNetwork.getRetrofit()
    private val service = WeatherService(api)
    private val mRepository = WeatherRepository(service)

    override fun onCreate(savedInstanceState: Bundle?) {
        setupViewModel()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentWeatherBinding.inflate(inflater, container, false)
        checkLatLon()
        setHasOptionsMenu(true)
        setupObserver()
        setupTimer()
        return mBinding.root
    }

    private fun setupObserver() {

        mViewModel.weatherInfo.observe(viewLifecycleOwner) { result ->
            if (mViewModel.existQueries() && result.getOrNull() != null) {
                mBinding.weatherResult = result.getOrNull()!!
                with(userPrefs.unitPref) {
                    mViewModel.setMeasureUnit(this!!)
                    mBinding.currentMeasure = this
                }

            } else {
                mViewModel.isLoading.postValue(true)
            }
        }

        mViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                mBinding.loading.visibility = View.VISIBLE
                mBinding.weatherInfo.visibility = View.GONE
            } else {
                mBinding.loading.visibility = View.GONE
                mBinding.weatherInfo.visibility = View.VISIBLE
            }
        }
    }

    private fun refreshWeatherData() {
        CoroutineScope(Dispatchers.IO).launch {
            mViewModel.reloadWeatherData()
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProvider(
            this,
            WeatherViewModel.WeatherVieModelFactory(mRepository)
        )[WeatherViewModel::class.java]
    }

    private fun checkLatLon() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation
                .addOnSuccessListener { location: Location? ->
                    mViewModel.setLatLon(
                        location?.latitude.toString(),
                        location?.longitude.toString()
                    )
                    refreshWeatherData()
                }

            return
        }
    }

    private fun setupTimer() {
        mViewModel.setTimerForRequest()
    }

    //Options Menu

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> checkLatLon()
        }
        return true
    }

    override fun onStop() {
        mViewModel.cancelTimer()
        super.onStop()
    }

}