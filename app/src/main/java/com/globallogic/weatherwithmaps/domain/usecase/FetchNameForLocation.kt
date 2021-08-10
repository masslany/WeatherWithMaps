package com.globallogic.weatherwithmaps.domain.usecase

import com.globallogic.weatherwithmaps.domain.model.LocationModel
import com.globallogic.weatherwithmaps.domain.repository.WeatherRepository
import com.globallogic.weatherwithmaps.presentation.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNameForLocation @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    operator fun invoke(location: LocationModel): Flow<State<String>> = flow {
        emit(State.Loading)
        val response = weatherRepository.getNameForLocation(location)
        response
            .onSuccess {
                try {
                    emit(State.Success(it.first().name))
                } catch (e: NoSuchElementException) {
                    emit(State.Error(e))
                }

            }
            .onFailure {
                emit(State.Error(it))
            }
    }
}