package com.globallogic.weatherwithmaps.domain.repository

import com.globallogic.weatherwithmaps.data.remote.api.OpenWeatherApi
import com.globallogic.weatherwithmaps.data.remote.response.location.LocationResponse
import com.globallogic.weatherwithmaps.data.remote.response.weather.WeatherResponse
import com.globallogic.weatherwithmaps.di.IoDispatcher
import com.globallogic.weatherwithmaps.domain.model.LocationModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {
    override suspend fun getWeatherData(location: LocationModel): Result<WeatherResponse> {
        return try {
            withContext(ioDispatcher) {
                Result.success(
                    api.getWeatherData(location.latitude, location.longitude)
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNameForLocation(location: LocationModel): Result<LocationResponse> {
        return try {
            withContext(ioDispatcher) {
                Result.success(
                    api.getLocationByCoordinates(location.latitude, location.longitude)
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}