package com.praveen.weatherhub.model.networkmodel

/**
 * Response models of API Services
 *
 * {
"data": [
{
"wind_cdir": "W",
"rh": 45,
"pod": "d",
"lon": -121.93579,
"pres": 997.2,
"timezone": "America/Los_Angeles",
"ob_time": "2018-07-28 20:48",
"country_code": "US",
"clouds": 25,
"vis": 10,
"state_code": "CA",
"wind_spd": 3.6,
"lat": 37.70215,
"wind_cdir_full": "west",
"slp": 1011.8,
"datetime": "2018-07-28:20",
"ts": 1532810880,
"station": "D4201",
"h_angle": 0,
"dewpt": 18.8,
"uv": 8.69044,
"dni": 852.242,
"wind_dir": 270,
"elev_angle": 70.8028,
"ghi": 945.621,
"dhi": 140.742,
"precip": null,
"city_name": "Dublin",
"weather": {
"icon": "c01d",
"code": "800",
"description": "Clear sky"
},
"sunset": "03:20",
"temp": 32.2,
"sunrise": "13:08",
"app_temp": 33.7
}
],
"count": 1
}
 */
data class WeatherResponse(
        val data: List<CurrentWeatherItem?>? = null,
        val count: Int? = null
)

data class CurrentWeatherItem(
        val sunrise: String? = null,
        val pod: String? = null,
        val pres: Double? = null,
        val timezone: String? = null,
        val ob_time: String? = null,
        val lon: Double? = null,
        val clouds: Double? = null,
        val wind_spd: Double? = null,
        val datetime: String? = null,
        val hAngle: Double? = null,
        val city_name: String? = null,
        val precip: Double? = null,
        val station: String? = null,
        val weather: CurrentWeatherData? = null,
        val elevAngle: Double? = null,
        val lat: Double? = null,
        val dni: Double? = null,
        val vis: Double? = null,
        val uv: Double? = null,
        val temp: Double? = null,
        val dhi: Double? = null,
        val ghi: Double? = null,
        val app_temp: Double? = null,
        val dewpt: Double? = null,
        val wind_cdir: String? = null,
        val country_code: String? = null,
        val rh: Double? = null,
        val slp: Double? = null,
        val sunset: String? = null,
        val state_code: String? = null,
        val wind_cdir_full: String? = null,
        val ts: Long? = null
)

data class CurrentWeatherData(
        val code: String? = null,
        val icon: String? = null,
        val description: String? = null
)

