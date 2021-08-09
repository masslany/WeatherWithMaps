package com.globallogic.weatherwithmaps.domain.repository

import com.globallogic.weatherwithmaps.data.remote.response.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Result<WeatherResponse>
}