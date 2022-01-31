package com.globant.carlosmunoz.myweather.utils

import android.content.Context
import android.content.SharedPreferences

class UserPrefs(context: Context) {
    //weather api provides three measurements: standard, metric, imperial

    private val UNITS = "standard"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(UNITS, Context.MODE_PRIVATE)

    var unitPref: String?
        get() = preferences.getString(UNITS, "standard")
        set(value) = preferences.edit().putString(UNITS, value).apply()

}