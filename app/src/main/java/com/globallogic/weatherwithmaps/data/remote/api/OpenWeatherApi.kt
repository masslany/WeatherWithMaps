package com.globallogic.weatherwithmaps.data.remote.api

import com.globallogic.weatherwithmaps.BuildConfig
import com.globallogic.weatherwithmaps.data.remote.response.Weather
import com.globallogic.weatherwithmaps.data.remote.response.WeatherResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherApi {

    @GET("/data/2.5/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely,hourly,alerts",
        @Query("appid") appid: String = BuildConfig.OPEN_WEATHER_API
    ): WeatherResponse
}