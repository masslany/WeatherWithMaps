package com.globallogic.weatherwithmaps.presentation.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.RequestManager
import com.globallogic.weatherwithmaps.R
import com.globallogic.weatherwithmaps.data.remote.response.weather.WeatherResponse
import com.globallogic.weatherwithmaps.databinding.FragmentWeatherBinding
import com.globallogic.weatherwithmaps.domain.model.Location
import com.globallogic.weatherwithmaps.presentation.util.Permission
import com.globallogic.weatherwithmaps.presentation.util.PermissionManager
import com.globallogic.weatherwithmaps.presentation.util.State
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val permissionManager = PermissionManager.from(this)

    @Inject
    lateinit var glide: RequestManager

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

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
                binding.tvCityName.text = getString(R.string.no_location_name)
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
                val icon = state.data.current.weather.first().icon
                val iconUrl = "http://openweathermap.org/img/wn/$icon.png"
                glide.load(iconUrl).into(binding.ivWeatherIcon)

                binding.tvCondition.text = state.data.current.weather.first().description
                binding.tvTemperature.text =
                    getString(R.string.temperature_celc, state.data.current.temp.toString())
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

       permissionManager
           .request(Permission.Location)
           .rationale(getString(R.string.location_permission_rationale))
           .checkPermission { granted ->
                if(granted) {
                    tomtomMap.isMyLocationEnabled = true
                    placeMarkerOnUserLocation(tomtomMap)
                } else {
                    placeMarkerOnDefaultLocation(tomtomMap)
                }
           }

        tomtomMap.addOnMarkerDragListener(onMarkerDragListener)
    }

    @SuppressLint("MissingPermission")
    private fun placeMarkerOnUserLocation(tomtomMap: TomtomMap) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: android.location.Location? ->
                if (location != null) {
                    viewModel.onLocationChanged(Location(location.latitude, location.longitude))

                    val markerBuilder = MarkerBuilder(
                        LatLng(location.latitude, location.longitude)
                    )
                        .draggable(true)
                    tomtomMap.addMarker(markerBuilder)
                } else {
                    viewModel.onLocationChanged(Location(0.0, 0.0))

                    val markerBuilder = MarkerBuilder(
                        LatLng(0.0, 0.0)
                    )
                        .draggable(true)
                    tomtomMap.addMarker(markerBuilder)
                }
            }
            .addOnFailureListener {
                viewModel.onLocationChanged(Location(0.0, 0.0))

                val markerBuilder = MarkerBuilder(
                    LatLng(0.0, 0.0)
                )
                    .draggable(true)
                tomtomMap.addMarker(markerBuilder)
            }
    }

    private fun placeMarkerOnDefaultLocation(tomtomMap: TomtomMap) {
        viewModel.onLocationChanged(Location(0.0, 0.0))

        val markerBuilder = MarkerBuilder(
            LatLng(0.0, 0.0)
        )
            .draggable(true)
        tomtomMap.addMarker(markerBuilder)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}