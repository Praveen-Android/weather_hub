package com.praveen.weatherhub.model.locationmodel

import com.praveen.weatherhub.model.locationmodel.bounds.Bounds
import com.praveen.weatherhub.model.locationmodel.viewport.Viewport


data class LocationResponse(
        val results: List<Result>,
        val status: String
)

data class Result(
        val address_components: List<AddressComponent>,
        val formatted_address: String,
        val geometry: Geometry,
        val place_id: String,
        val types: List<String>
)

data class Geometry(
        val bounds: Bounds,
        val location: Location,
        val location_type: String,
        val viewport: Viewport
)


data class Location(
        val lat: Double,
        val lng: Double
)

data class AddressComponent(
        val long_name: String,
        val short_name: String,
        val types: List<String>
)