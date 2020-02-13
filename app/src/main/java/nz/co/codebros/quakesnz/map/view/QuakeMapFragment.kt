package nz.co.codebros.quakesnz.map.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.AndroidSupportInjection
import nz.co.codebros.quakesnz.map.QuakeMapViewModel
import nz.co.vilemob.daggerviewmodel.ViewModelFactory
import javax.inject.Inject

class QuakeMapFragment : SupportMapFragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<QuakeMapViewModel>

    private var marker: Marker? = null

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)
        AndroidSupportInjection.inject(this)

        ViewModelProvider(this, viewModelFactory)
                .get(QuakeMapViewModel::class.java)
                .quakeMapState.observe(viewLifecycleOwner, Observer {
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
        })

        when (bundle) {
            null -> getMapAsync {
                it.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(LatLng(-41.3090732, 175.1858282), 4.5f))
            }
        }
    }
}
