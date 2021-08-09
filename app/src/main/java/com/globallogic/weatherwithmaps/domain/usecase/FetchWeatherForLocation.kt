package com.globallogic.weatherwithmaps.domain.usecase

import com.globallogic.weatherwithmaps.data.remote.response.WeatherResponse
import com.globallogic.weatherwithmaps.domain.model.Location
import com.globallogic.weatherwithmaps.domain.repository.WeatherRepository
import com.globallogic.weatherwithmaps.presentation.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchWeatherForLocation @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    operator fun invoke(location: Location): Flow<State<WeatherResponse>> = flow {
        emit(State.Loading)
        val response = weatherRepository.getWeatherData(location.latitude, location.longitude)
        response
            .onSuccess {
                emit(State.Success(it))
            }
            .onFailure {
                emit(State.Error(it))
            }
    }
}