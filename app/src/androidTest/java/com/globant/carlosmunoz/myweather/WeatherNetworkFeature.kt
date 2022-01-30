package com.globant.carlosmunoz.myweather

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.globant.carlosmunoz.myweather.ui.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class WeatherNetworkFeature {

    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
    @Rule get

    @Test
    fun displayPrincipalUI() {
        assertDisplayed("MyWeather")
        assertDisplayed("Santiago, CL")
        assertDisplayed("T:")
        assertDisplayed("WeatherNetwork")
        assertDisplayed("Settings")
    }


    @Test
    fun displayLoadingWhenIsFetching() {
       assertDisplayed(R.id.loading)
    }
}