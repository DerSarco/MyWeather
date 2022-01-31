package com.globant.carlosmunoz.myweather.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.globant.carlosmunoz.myweather.ui.MainActivity
import com.globant.carlosmunoz.myweather.viewmodels.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FusedLocationHelper {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(
            applicationContext
        )


    companion object {
        lateinit var applicationContext: Context
        lateinit var activity: MainActivity
        private const val requestPermissionCode = 10
        fun getLastLocation(weatherViewModel: WeatherViewModel? = null):Boolean {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission()
                return false
            } else {
                FusedLocationHelper().fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        val location = it
                        if (location != null) {
                            if (weatherViewModel != null) {
                                with(weatherViewModel) {
                                    setLatitude(it.latitude.toString())
                                    setLongitude(it.longitude.toString())
                                }
                            }
                        }
                    }
                return true
            }
        }

        private fun requestPermission() {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestPermissionCode
            )

        }
    }

}


