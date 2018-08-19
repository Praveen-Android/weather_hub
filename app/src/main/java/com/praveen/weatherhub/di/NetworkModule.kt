package com.praveen.weatherhub.di

import com.google.gson.Gson
import com.praveen.weatherhub.api.ApiEndPoints
import com.praveen.weatherhub.api.GeocodingService
import com.praveen.weatherhub.api.WeatherService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {

    private val connectionTimeout = 60 //in seconds
    private val readTimeout = 60 //in seconds

    @Provides
    @Singleton
    fun provideWeatherBitService(@Named("CURRENT_WEATHER_API")retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

    @Provides
    @Singleton
    @Named("CURRENT_WEATHER_API")
    fun provideRetrofitClient(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(ApiEndPoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(connectionTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @Named("GEOCODING_API")
    fun provideWeatherForecastAPIRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(ApiEndPoints.GEOCODING_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()


    @Provides
    @Singleton
    fun provideRemoteGeocodingService(@Named("GEOCODING_API") retrofit: Retrofit): GeocodingService =
            retrofit.create(GeocodingService::class.java)

}