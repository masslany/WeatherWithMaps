package com.globallogic.weatherwithmaps.domain.repository

import com.globallogic.weatherwithmaps.data.remote.response.location.LocationResponse
import com.globallogic.weatherwithmaps.data.remote.response.weather.WeatherResponse
import com.globallogic.weatherwithmaps.domain.model.LocationModel

interface WeatherRepository {
    suspend fun getWeatherData(location: LocationModel): Result<WeatherResponse>
    suspend fun getNameForLocation(location: LocationModel): Result<LocationResponse>
}