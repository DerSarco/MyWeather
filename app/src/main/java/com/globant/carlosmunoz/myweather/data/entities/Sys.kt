package com.globant.carlosmunoz.myweather.data.entities


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val country: String
)