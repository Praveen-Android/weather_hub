package com.praveen.weatherhub.api

import com.praveen.weatherhub.model.locationmodel.LocationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {

    @GET("json")
    fun requestCityAddressByName(
            @Query("address") address: String
    ): Single<LocationResponse>


    @GET("json")
    fun requestCityAddressByPostalCode(
            @Query("postal_code") address: String
    ): Single<LocationResponse>

    @GET("json")
    fun requestCityAddressByLatLng(
            @Query("latlng") address: String
    ): Single<LocationResponse>

}