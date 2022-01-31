package com.globant.carlosmunoz.myweather.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.globant.carlosmunoz.myweather.data.entities.WeatherResult

class WeatherBindings {

    companion object {
        @BindingAdapter("android:setCityAndCountry")
        @JvmStatic
        fun setCityAndCountry(textView: TextView, weather: WeatherResult? = null) {
            weather?.let {
                "${weather.name}, ${weather.sys.country}".also { textView.text = it }
            }
        }

        @BindingAdapter("android:setCurrentTemperature")
        @JvmStatic
        fun setCurrentTemperature(textView: TextView, currentTemp: String = "") {
            "Current: $currentTemp".also { textView.text = it }
        }

        @BindingAdapter("android:setMin", "android:setMax")
        @JvmStatic
        fun setMinMax(textView: TextView, min: String = "", max: String = "") {
            "Min: $min / Max: $max".also { textView.text = it }
        }

        @BindingAdapter("android:setCurrentMeasure")
        @JvmStatic
        fun setCurrentMeasure(textView: TextView, measure: String? = null) {
            if (measure != null) {
                "Your current measure is: ${measure.replaceFirstChar { c: Char -> c.uppercase() }}".also {
                    textView.text = it
                }
            }
        }

        @BindingAdapter("android:setIconFromResult")
        @JvmStatic
        fun setIconFromResult(imageView: ImageView, icon: String? = null) {
            icon?.let {
                imageView.load(
                    "https://openweathermap.org/img/wn/$it@2x.png"
                ) {
                    crossfade(600)
                }
            }
        }
    }

}

