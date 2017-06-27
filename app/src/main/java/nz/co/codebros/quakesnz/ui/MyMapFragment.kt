package nz.co.codebros.quakesnz.ui

import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import nz.co.codebros.quakesnz.model.Geometry

class MyMapFragment : SupportMapFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val latitude = arguments.getDouble(ARG_LATITUDE)
        val longitude = arguments.getDouble(ARG_LONGITUDE)
        val location = LatLng(latitude, longitude)
        when (savedInstanceState) {
            null -> getMapAsync { googleMap ->
                googleMap.addMarker(MarkerOptions().position(location))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 6f))
            }
            else -> getMapAsync { googleMap ->
                googleMap.addMarker(MarkerOptions().position(location))
            }
        }
    }

    companion object {
        val ARG_LATITUDE = "arg_latitude"
        val ARG_LONGITUDE = "arg_longitude"

        fun newInstance(geometry: Geometry): MyMapFragment {
            val coordinates = geometry.coordinates

            val bundle = Bundle()
            bundle.putDouble(ARG_LATITUDE, coordinates.latitude)
            bundle.putDouble(ARG_LONGITUDE, coordinates.longitude)

            val fragment = MyMapFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
