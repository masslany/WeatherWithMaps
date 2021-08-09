package com.globallogic.weatherwithmaps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.globallogic.weatherwithmaps.databinding.FragmentMainBinding
import com.tomtom.online.sdk.map.*

class WeatherFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getAsyncMap(this)
    }

    override fun onMapReady(tomtomMap: TomtomMap) {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}