package com.praveen.weatherhub.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = arrayOf(CurrentWeather::class, WeatherForecast::class), version = 1, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {

    abstract fun weatherHubDao(): WeatherHubDao

    companion object {

        @Volatile private var INSTANCE: WeatherDb? = null

        fun getInstance(context: Context): WeatherDb =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        WeatherDb::class.java, DbDescriptor.WEATHER_HUB_DATABASE)
                        .build()
    }
}