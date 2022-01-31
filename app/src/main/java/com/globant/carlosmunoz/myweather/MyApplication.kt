package com.globant.carlosmunoz.myweather

import android.app.Application
import com.globant.carlosmunoz.myweather.utils.UserPrefs

val userPrefs: UserPrefs by lazy {
    MyApplication.preferences!!
}

class MyApplication : Application() {

    companion object {
        var preferences: UserPrefs? = null
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferences = UserPrefs(applicationContext)
    }
}