package com.globant.carlosmunoz.myweather

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class WeatherFeature {

    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
    @Rule get

    @Test
    fun displayPrincipalUI() {
        assertDisplayed("MyWeather")
        assertDisplayed("Santiago, CL")
        assertDisplayed("T:")
        assertDisplayed("Weather")
        assertDisplayed("Settings")
    }
}