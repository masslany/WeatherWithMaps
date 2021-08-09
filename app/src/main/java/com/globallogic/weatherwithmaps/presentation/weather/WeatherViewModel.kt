package com.globallogic.weatherwithmaps.presentation.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.globallogic.weatherwithmaps.data.remote.response.weather.WeatherResponse
import com.globallogic.weatherwithmaps.domain.model.Location
import com.globallogic.weatherwithmaps.domain.usecase.FetchNameForLocation
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
    private val fetchWeatherForLocation: FetchWeatherForLocation,
    private val fetchNameForLocation: FetchNameForLocation
) : ViewModel() {

    private val _weather = MutableLiveData<State<WeatherResponse>>(State.Loading)
    val weather: LiveData<State<WeatherResponse>> = _weather

    private val _locationName = MutableLiveData<State<String>>(State.Loading)
    val locationName: LiveData<State<String>> = _locationName

    init {
        onLocationChanged(Location(53.4138608, 14.5557252))
    }

    fun onLocationChanged(location: Location) {
        val weather = fetchWeatherForLocation(location)
        val name = fetchNameForLocation(location)
        viewModelScope.launch {
            weather.collect { state ->
                _weather.value = state
            }
            name.collect { state ->
                _locationName.value = state
            }
        }
    }
}