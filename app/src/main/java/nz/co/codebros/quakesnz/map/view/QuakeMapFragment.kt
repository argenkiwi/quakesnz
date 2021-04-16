package nz.co.codebros.quakesnz.map.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import nz.co.codebros.quakesnz.map.QuakeMapViewModel

@AndroidEntryPoint
class QuakeMapFragment : SupportMapFragment() {

    private val viewModel: QuakeMapViewModel by viewModels()

    private var marker: Marker? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveState.observe(viewLifecycleOwner) {
            it?.apply {
                when (coordinates) {
                    null -> {
                        marker?.remove()
                        marker = null
                    }
                    else -> coordinates.run { LatLng(latitude, longitude) }.let { latLng ->
                        getMapAsync { googleMap ->
                            when (marker) {
                                null -> {
                                    marker = googleMap.addMarker(MarkerOptions().position(latLng))
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6f))
                                }
                                else -> {
                                    marker?.position = latLng
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                                }
                            }
                        }
                    }
                }
            }
        }

        when (savedInstanceState) {
            null -> getMapAsync {
                it.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(LatLng(-41.3090732, 175.1858282), 4.5f))
            }
        }
    }
}
