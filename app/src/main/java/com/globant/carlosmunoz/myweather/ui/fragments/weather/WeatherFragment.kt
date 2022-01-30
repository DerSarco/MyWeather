package com.globant.carlosmunoz.myweather.ui.fragments.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globant.carlosmunoz.myweather.R
import com.globant.carlosmunoz.myweather.data.api.WeatherNetwork
import com.globant.carlosmunoz.myweather.databinding.FragmentWeatherBinding
import com.globant.carlosmunoz.myweather.repository.WeatherRepository
import com.globant.carlosmunoz.myweather.service.WeatherService
import com.globant.carlosmunoz.myweather.viewmodels.WeatherVieModelFactory
import com.globant.carlosmunoz.myweather.viewmodels.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherFragment : Fragment() {

    private lateinit var mBinding: FragmentWeatherBinding
    private lateinit var mViewModel: WeatherViewModel

    private val api = WeatherNetwork.getRetrofit()
    private val service = WeatherService(api)
    private val mRepository = WeatherRepository(service)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentWeatherBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            Log.d("enter", "hasPermissions")
            setupViewModel()
            setHasOptionsMenu(true)
            setupObserver()
            refreshWeatherData()
        }
    }

    private fun setupObserver() {
        mViewModel.weatherInfo.observe(viewLifecycleOwner) { result ->
            Log.d("it", result.toString())
            if (result.getOrNull() != null) {
                result.map {
                    mBinding.weatherResult = it
                }
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
            mViewModel.reloadWeatherInfo()
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProvider(
            this,
            WeatherVieModelFactory(mRepository)
        )[WeatherViewModel::class.java]
    }


    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireActivity().applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> refreshWeatherData()
        }
        return true
    }

    override fun onDestroy() {
        Log.d("onDestroy", "ejecutado")
        super.onDestroy()
    }
}