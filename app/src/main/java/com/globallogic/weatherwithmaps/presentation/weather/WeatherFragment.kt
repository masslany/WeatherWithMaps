package com.globallogic.weatherwithmaps.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.globallogic.weatherwithmaps.R
import com.globallogic.weatherwithmaps.data.remote.response.weather.WeatherResponse
import com.globallogic.weatherwithmaps.databinding.FragmentWeatherBinding
import com.globallogic.weatherwithmaps.domain.model.Location
import com.globallogic.weatherwithmaps.presentation.util.State
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getAsyncMap(this)

        viewModel.weather.observe(viewLifecycleOwner) { state ->
            handleWeatherState(state)
        }
        viewModel.locationName.observe(viewLifecycleOwner) { state ->
            handleLocationNameState(state)
        }

    }

    private fun handleLocationNameState(state: State<String>) {
        when (state) {
            State.Loading -> {
            }
            is State.Success -> {
                binding.tvCityName.text = state.data
            }
            is State.Error -> {
                binding.tvCityName.text = "Error"
            }
        }
    }

    private fun handleWeatherState(state: State<WeatherResponse>) {
        when (state) {
            State.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is State.Success -> {
                binding.progressBar.visibility = View.GONE

            }

            is State.Error -> {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private var onMarkerDragListener: TomtomMapCallback.OnMarkerDragListener =
        object : TomtomMapCallback.OnMarkerDragListener {
            override fun onStartDragging(marker: Marker) {}

            override fun onStopDragging(marker: Marker) {
                val location = Location(marker.position.latitude, marker.position.longitude)
                viewModel.onLocationChanged(location)
            }

            override fun onDragging(marker: Marker) {}
        }

    override fun onMapReady(tomtomMap: TomtomMap) {
        val markerBuilder = MarkerBuilder(LatLng(53.4138608, 14.5557252))
            .draggable(true)
        tomtomMap.addMarker(markerBuilder)
        tomtomMap.addOnMarkerDragListener(onMarkerDragListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}