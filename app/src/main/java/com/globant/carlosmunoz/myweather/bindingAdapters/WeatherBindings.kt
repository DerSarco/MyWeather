package com.globant.carlosmunoz.myweather.bindingAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.globant.carlosmunoz.myweather.data.entities.WeatherResult

class WeatherBindings {

    companion object {
        @BindingAdapter("setCityAndCountry")
        @JvmStatic
        fun setCityAndCountry(textView: TextView, weather: WeatherResult? = null) {
            weather?.let {
                "${weather.name}, ${weather.sys.country}".also { textView.text = it }
            }
        }

        @BindingAdapter("setCurrentTemperature")
        @JvmStatic
        fun setCurrentTemperature(textView: TextView, currentTemp: String = "") {
            "Current: $currentTemp".also { textView.text = it }
        }

        @BindingAdapter("setMin", "setMax")
        @JvmStatic
        fun setMinMax(textView: TextView, min: String = "", max: String = "") {
            "Min: $min / Max: $max".also { textView.text = it }
        }
    }

}
