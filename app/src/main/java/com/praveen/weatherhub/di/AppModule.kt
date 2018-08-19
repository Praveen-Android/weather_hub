package com.praveen.weatherhub.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.praveen.weatherhub.WeatherApplication
import com.praveen.weatherhub.repo.WeatherRepository
import com.praveen.weatherhub.prefs.PreferenceHelper
import com.praveen.weatherhub.prefs.PreferenceHelperImpl
import com.praveen.weatherhub.viewmodel.WeatherViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val weatherApplication: WeatherApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = weatherApplication

    @Provides
    @Singleton
    fun provideViewModelFactory(weatherRepository: WeatherRepository): WeatherViewModelFactory {
        return WeatherViewModelFactory(weatherRepository)
    }

    @Provides
    @Singleton
     fun providePreferencesHelper(preferenceHelperImpl: PreferenceHelperImpl): PreferenceHelper {
        return preferenceHelperImpl
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()
}