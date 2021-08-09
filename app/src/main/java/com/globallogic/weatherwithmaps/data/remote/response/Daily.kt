package com.globallogic.weatherwithmaps.data.remote.response


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    @SerializedName("humidity")
    val humidity: Double,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    @SerializedName("moonrise")
    val moonrise: Double,
    @SerializedName("moonset")
    val moonset: Int,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("pressure")
    val pressure: Double,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("sunrise")
    val sunrise: Double,
    @SerializedName("sunset")
    val sunset: Double,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("uvi")
    val uvi: Double,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind_deg")
    val windDeg: Double,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
)