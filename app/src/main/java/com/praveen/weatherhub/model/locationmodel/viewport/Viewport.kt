package com.praveen.weatherhub.model.locationmodel.viewport


data class Viewport(
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