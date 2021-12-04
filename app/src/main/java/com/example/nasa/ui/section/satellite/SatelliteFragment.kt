package com.example.nasa.ui.section.satellite

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.nasa.R
import com.example.nasa.databinding.FragmentSatelliteBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener
import kotlinx.android.synthetic.main.fragment_satellite.*
import com.google.android.gms.maps.model.Marker





@AndroidEntryPoint
class SatelliteFragment : Fragment() {

    lateinit var viewBinding: FragmentSatelliteBinding
    val viewModel: SatelliteViewModel by viewModels()

    var mapFragment: SupportMapFragment? = null
    var map: GoogleMap? = null
    var marker: Marker? = null

    private val callback = OnMapReadyCallback { googleMap ->

        viewBinding.myLocationBtn.setOnClickListener{
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
//                    googleMap.uiSettings.isMyLocationButtonEnabled = true
                googleMap.isMyLocationEnabled = true

                // Check if we were successful in obtaining the map.
                if (googleMap != null) {
                    googleMap.setOnMyLocationChangeListener(OnMyLocationChangeListener { arg0 -> googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(arg0.latitude, arg0.longitude))
                            .title("It's Me!")
                    )
                    })
                }

            } else {
            }
        }

        viewModel.getSatelliteViewModel("43694").observe(viewLifecycleOwner,{
            viewBinding.nameSatellite.text = "name: ${it.tle.name}"

            val sydney = LatLng(it.geodetic.latitude, it.geodetic.longitude)


            googleMap.clear()

            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID


            googleMap.addMarker(
                MarkerOptions()
                .position(sydney)
                .title("${it.tle.name}")
                .snippet(""))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.satellite_icon))

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSatelliteBinding.inflate(inflater)
        val typeface = Typeface.createFromAsset(requireActivity().assets,"pixel.ttf")


        viewBinding.nameSatellite.typeface = typeface


        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}