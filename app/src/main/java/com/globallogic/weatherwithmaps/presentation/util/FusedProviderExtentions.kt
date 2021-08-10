package com.globallogic.weatherwithmaps.presentation.util

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

// Extension function on FusedLocationProviderClient, returns last known location
@ExperimentalCoroutinesApi
@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitLastLocation(): Location? =

    // Create a new coroutine that can be cancelled
    suspendCancellableCoroutine<Location?> { continuation ->

        // Add listeners that will resume the execution of this coroutine
        lastLocation.addOnSuccessListener { location ->
            // Resume coroutine and return location
            location?.let { continuation.resume(it) {} }
        }.addOnFailureListener { e ->
            // Resume the coroutine by throwing an exception
            continuation.resumeWithException(e)
        }

        // End of the suspendCancellableCoroutine block. This suspends the
        // coroutine until one of the callbacks calls the continuation parameter.
    }
