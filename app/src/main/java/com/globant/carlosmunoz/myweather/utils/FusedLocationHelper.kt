package com.globant.carlosmunoz.myweather.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.globant.carlosmunoz.myweather.ui.MainActivity
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
        fun getLastLocation(): Boolean{
            return if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission()
                true
            }else{
                false
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


