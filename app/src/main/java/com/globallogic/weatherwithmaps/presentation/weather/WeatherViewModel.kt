package com.globallogic.weatherwithmaps.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.weatherwithmaps.data.remote.response.WeatherResponse
import com.globallogic.weatherwithmaps.domain.model.Location
import com.globallogic.weatherwithmaps.domain.usecase.FetchWeatherForLocation
import com.globallogic.weatherwithmaps.presentation.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherForLocation: FetchWeatherForLocation
) : ViewModel() {

    private val _weather = MutableStateFlow<State<WeatherResponse>>(State.Loading)
    val weather: StateFlow<State<WeatherResponse>> = _weather

    init {
        onLocationChanged(Location(53.4138608,14.5557252))
    }

    fun onLocationChanged(location: Location) {
        val response = fetchWeatherForLocation(location)
        viewModelScope.launch {
            response.collect { state ->
                _weather.value = state
            }
        }
    }
}