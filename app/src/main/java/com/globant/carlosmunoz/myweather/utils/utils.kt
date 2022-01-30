package com.globant.carlosmunoz.myweather.utils

fun applyQueries(): HashMap<String, String> {
    val queries = hashMapOf<String,String>()
    queries["lat"] = Constants.QUERY_LAT
    queries["lon"] = Constants.QUERY_LON
    queries["appid"] = Constants.API_KEY
    return queries
}