package nz.co.codebros.quakesnz.map

import android.content.Context
import android.os.Bundle
import android.view.View

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import javax.inject.Inject

import dagger.android.support.AndroidSupportInjection
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import nz.co.codebros.quakesnz.core.model.Coordinates
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.core.model.Geometry
import nz.co.codebros.quakesnz.repository.FeatureRepository

/**
 * Created by leandro on 29/06/17.
 */

class QuakeMapFragment : SupportMapFragment() {

    @Inject
    internal lateinit var repository: FeatureRepository

    private var disposable: Disposable? = null
    private var marker: Marker? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)
        if (bundle == null) {
            getMapAsync { googleMap ->
                googleMap.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(LatLng(-41.3090732, 175.1858282), 4.5f))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable = repository.subscribe(Consumer { (geometry) ->
            getMapAsync { googleMap ->
                val coordinates = geometry.coordinates
                val latLng = LatLng(
                        coordinates.latitude,
                        coordinates.longitude
                )
                if (marker == null) {
                    marker = googleMap.addMarker(MarkerOptions().position(latLng))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6f))
                } else {
                    marker!!.position = latLng
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
    }
}
