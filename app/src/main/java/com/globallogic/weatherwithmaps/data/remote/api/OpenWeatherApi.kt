package com.globallogic.weatherwithmaps.data.remote.api

import com.globallogic.weatherwithmaps.BuildConfig
import com.globallogic.weatherwithmaps.data.remote.response.location.LocationResponse
import com.globallogic.weatherwithmaps.data.remote.response.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherApi {

    @GET("/data/2.5/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely,hourly,alerts",
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = BuildConfig.OPEN_WEATHER_API
    ): WeatherResponse

    @GET("/geo/1.0/reverse")
    suspend fun getLocationByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.OPEN_WEATHER_API
    ): LocationResponse
}