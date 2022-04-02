package com.globant.carlosmunoz.myweather.utils

import android.content.Context
import android.content.SharedPreferences

class UserPrefs(context: Context) {
    //weather api provides three measurements: standard, metric, imperial

    private val units = "standard"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(units, Context.MODE_PRIVATE)

    var unitPref: String?
        get() = preferences.getString(units, "standard")
        set(value) = preferences.edit().putString(units, value).apply()

}