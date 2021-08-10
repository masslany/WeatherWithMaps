package com.globallogic.weatherwithmaps.domain.repository

import android.annotation.SuppressLint
import com.globallogic.weatherwithmaps.domain.model.LocationModel
import com.globallogic.weatherwithmaps.presentation.util.awaitLastLocation
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocationRepositoryImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationRepository {

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    override fun getUserLocation(): Flow<LocationModel> = flow {
        var userLocation = LocationModel(0.0, 0.0)
        emit(userLocation)
        val loc = fusedLocationProviderClient.awaitLastLocation()
        if (loc != null) {
            userLocation = LocationModel(loc.latitude, loc.longitude)
            emit(userLocation)
        }
    }
}