package com.globallogic.weatherwithmaps.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.globallogic.weatherwithmaps.R
import com.globallogic.weatherwithmaps.databinding.FragmentWeatherBinding
import com.globallogic.weatherwithmaps.presentation.util.State
import com.tomtom.online.sdk.map.MapFragment
import com.tomtom.online.sdk.map.OnMapReadyCallback
import com.tomtom.online.sdk.map.TomtomMap
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
            println(state)
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
        viewModel.locationName.observe(viewLifecycleOwner) { state ->
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


    }

    override fun onMapReady(tomtomMap: TomtomMap) {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}