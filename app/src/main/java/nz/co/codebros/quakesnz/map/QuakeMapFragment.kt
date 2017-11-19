package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.*
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.Provides
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import nz.co.codebros.quakesnz.core.data.Coordinates
import nz.co.codebros.quakesnz.repository.FeatureRepository
import javax.inject.Inject

/**
 * Created by Leandro on 9/11/2017.
 */

class QuakeMapFragment : SupportMapFragment() {

    @Inject
    internal lateinit var viewModel: ViewModel

    private var marker: Marker? = null

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)

        try {
            viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        } catch (e: Throwable) {
            AndroidSupportInjection.inject(this)
        }

        Transformations.map(viewModel.coordinates, {
            LatLng(it.latitude, it.longitude)
        }).observe(this, Observer {
            it?.let { latLng ->
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

    internal class ViewModel(
            coordinates: MutableLiveData<Coordinates>,
            repository: FeatureRepository
    ) : android.arch.lifecycle.ViewModel() {
        val coordinates: LiveData<Coordinates> = coordinates

        private val disposables = CompositeDisposable()

        init {
            disposables.add(repository.observable
                    .map { it.geometry.coordinates }
                    .subscribe({ coordinates.value = it }))
        }

        override fun onCleared() {
            super.onCleared()
            disposables.dispose()
        }

        class Factory @Inject constructor(
                private val coordinates: MutableLiveData<Coordinates>,
                private val repository: FeatureRepository
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel?> create(modelClass: Class<T>) =
                    ViewModel(coordinates, repository) as T
        }
    }

    @dagger.Module
    internal object Module {
        @JvmStatic
        @Provides
        fun coordinatesLiveData() = MutableLiveData<Coordinates>()

        @JvmStatic
        @Provides
        fun viewModel(
                fragment: QuakeMapFragment,
                factory: ViewModel.Factory
        ) = ViewModelProviders.of(fragment, factory).get(ViewModel::class.java)
    }
}
