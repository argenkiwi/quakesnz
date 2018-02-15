package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by Leandro on 9/11/2017.
 */
class QuakeMapFragment : SupportMapFragment() {

    @Inject
    internal lateinit var viewModel: QuakeMapViewModel

    private var marker: Marker? = null

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)

        try {
            viewModel = ViewModelProviders.of(this).get(QuakeMapViewModel::class.java)
        } catch (e: Throwable) {
            AndroidSupportInjection.inject(this)
        }

        viewModel.stateLiveData.observe(this, Observer {
            it?.coordinates?.apply {
                val latLng = LatLng(latitude, longitude)
                getMapAsync {
                    when (marker) {
                        null -> {
                            marker = it.addMarker(MarkerOptions().position(latLng))
                            it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6f))
                        }
                        else -> {
                            marker?.position = latLng
                            it.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                        }
                    }
                }
            }
        })

        if (bundle == null) getMapAsync {
            it.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(LatLng(-41.3090732, 175.1858282), 4.5f))
        }
    }
}
