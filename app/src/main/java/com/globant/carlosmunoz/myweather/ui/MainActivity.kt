package com.globant.carlosmunoz.myweather.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.globant.carlosmunoz.myweather.R
import com.globant.carlosmunoz.myweather.databinding.ActivityMainBinding


import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import com.globant.carlosmunoz.myweather.utils.FusedLocationHelper


class MainActivity : AppCompatActivity() {

    private lateinit var mNavController: NavController
    private lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        askPermissions()

    }

    //Permissions
    private fun askPermissions() {
        FusedLocationHelper.activity = this
        FusedLocationHelper.applicationContext = applicationContext
        FusedLocationHelper.getLastLocation()
        bindView()

    }

    private fun bindView() {
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

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() || super.onSupportNavigateUp()
    }
}