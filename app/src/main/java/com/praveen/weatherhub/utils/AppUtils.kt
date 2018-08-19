package com.praveen.weatherhub.utils

import android.content.Context
import com.praveen.weatherhub.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object AppUtils {

    private val VALID_CITY_REGEX = Pattern.compile("[a-zA-Z]+")

    fun getWeatherIcon(context: Context, weatherIconId: Int, partOfDay: String): String {
        val id = weatherIconId / 100
        var icon = ""
        if (weatherIconId == 800) {
            if (partOfDay == "d") {
                icon = context.getString(R.string.weather_sunny)
            } else if (partOfDay == "n") {
                icon = context.getString(R.string.weather_clear_night)
            }
        } else {
            when (id) {
                2 -> icon = context.getString(R.string.weather_thunder)
                3 -> icon = context.getString(R.string.weather_drizzle)
                7 -> icon = context.getString(R.string.weather_foggy)
                8 -> icon = context.getString(R.string.weather_cloudy)
                6 -> icon = context.getString(R.string.weather_snowy)
                5 -> icon = context.getString(R.string.weather_rainy)
            }
        }

        return icon
    }

    fun convertFahrenheitToCelsius(temperature: Double?): Double? = temperature?.let { ((temperature - 32) * 5) / 9 }

    fun convertCelsiusToFahrenheit(temperature: Double?): Double? = temperature?.let { temperature * 9 / 5 + 32 }

    private val mMDYearWithTimeFormatter = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("M/d/yyyy 'at' h:mm a", Locale.US)
        }
    }

    private fun getMDYearWithTimeFormatter(): ThreadLocal<SimpleDateFormat> {
        return mMDYearWithTimeFormatter
    }

    fun isValidCityInput(city: String): Boolean {
        val matches = VALID_CITY_REGEX.matcher(city).matches()
        return matches
    }

    fun dateFormatter(timestamp: Long?): String {
        return getMDYearWithTimeFormatter().get().format(Date(timestamp!!))
    }

    fun precisionHandler(value: Double?, unitSymbol: String): String {
        return "%.2f".format(value).plus(unitSymbol)
    }
}