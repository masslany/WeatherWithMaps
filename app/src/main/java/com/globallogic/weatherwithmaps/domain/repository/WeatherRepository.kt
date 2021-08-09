package com.globallogic.weatherwithmaps.domain.repository

import com.globallogic.weatherwithmaps.data.remote.response.location.LocationResponse
import com.globallogic.weatherwithmaps.data.remote.response.weather.WeatherResponse
import com.globallogic.weatherwithmaps.domain.model.Location

interface WeatherRepository {
    suspend fun getWeatherData(location: Location): Result<WeatherResponse>
    suspend fun getNameForLocation(location: Location): Result<LocationResponse>
}