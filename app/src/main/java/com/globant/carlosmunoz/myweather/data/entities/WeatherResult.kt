package com.globant.carlosmunoz.myweather.data.entities


import com.google.gson.annotations.SerializedName

data class WeatherResult(
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("weather")
    val weather: List<Weather>
)