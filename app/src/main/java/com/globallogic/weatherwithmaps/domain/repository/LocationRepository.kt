package com.globallogic.weatherwithmaps.domain.repository

import com.globallogic.weatherwithmaps.domain.model.LocationModel
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getUserLocation(): Flow<LocationModel>
}