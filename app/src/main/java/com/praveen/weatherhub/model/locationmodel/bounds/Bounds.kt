package com.praveen.weatherhub.model.locationmodel.bounds


data class Bounds(
        val northeast: Northeast,
        val southwest: Southwest
)

data class Northeast(
        val lat: Double,
        val lng: Double
)

data class Southwest(
        val lat: Double,
        val lng: Double
)