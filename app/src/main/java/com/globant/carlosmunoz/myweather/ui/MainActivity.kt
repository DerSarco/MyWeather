package com.globant.carlosmunoz.myweather.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.globant.carlosmunoz.myweather.R
import com.globant.carlosmunoz.myweather.databinding.ActivityMainBinding
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast

import androidx.core.content.ContextCompat
import com.globant.carlosmunoz.myweather.data.entities.Main
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var mNavController: NavController
    private lateinit var mBinding: ActivityMainBinding

    private val requestPermissionCode = 10
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        askPermissions()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        mNavController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.weatherFragment,
                R.id.settingsFragment
            )
        )
        mBinding.bottomNavigationView.setupWithNavController(mNavController)
        setupActionBarWithNavController(mNavController, appBarConfiguration)
        setContentView(mBinding.root)
    }

    //Permissions
    private fun askPermissions() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }


    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
        } else {
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    val location = it.result
                    if (location != null) {
                        Log.d(
                            "latitude/long",
                            "${location.latitude} ${location.longitude}"
                        )
                    }
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            10 -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.recreate()
                    Log.d("Acesss", "Done! $requestCode")
                } else {
                    Toast.makeText(
                        applicationContext,
                        "This app need permissions to get your current weather.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestPermissionCode
        )

    }


    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() || super.onSupportNavigateUp()
    }
}