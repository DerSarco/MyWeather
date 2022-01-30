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

class MainActivity : AppCompatActivity() {
    private lateinit var mNavController: NavController
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
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

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() || super.onSupportNavigateUp()
    }
}