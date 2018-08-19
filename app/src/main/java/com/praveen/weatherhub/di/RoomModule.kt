package com.praveen.weatherhub.di

import android.content.Context
import com.praveen.weatherhub.room.WeatherDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDb(context: Context) = WeatherDb.getInstance(context)
}