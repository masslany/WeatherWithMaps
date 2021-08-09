package com.globallogic.weatherwithmaps.domain.repository

import com.globallogic.weatherwithmaps.data.remote.api.OpenWeatherApi
import com.globallogic.weatherwithmaps.data.remote.response.WeatherResponse
import com.globallogic.weatherwithmaps.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, lon: Double): Result<WeatherResponse> {
        return try {
            withContext(ioDispatcher) {
                Result.success(api.getWeatherData(lat, lon))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}