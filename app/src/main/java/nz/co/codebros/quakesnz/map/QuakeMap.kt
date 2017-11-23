package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import dagger.Provides
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Coordinates
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by Leandro on 20/11/2017.
 */
internal interface QuakeMap {

    class ViewModel(
            featureObservable: Observable<Feature>,
            coordinates: MutableLiveData<Coordinates>
    ) : android.arch.lifecycle.ViewModel() {
        val coordinates: LiveData<Coordinates> = coordinates

        private val disposable = featureObservable
                .map { it.geometry.coordinates }
                .subscribe({ coordinates.value = it })

        override fun onCleared() {
            super.onCleared()
            disposable.dispose()
        }

        class Factory @Inject constructor(
                private val featureObservable: Observable<Feature>,
                private val coordinates: MutableLiveData<Coordinates>
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel?> create(modelClass: Class<T>) =
                    ViewModel(featureObservable, coordinates) as T
        }
    }

    @dagger.Module
    object Module {
        @JvmStatic
        @Provides
        fun coordinatesLiveData() = MutableLiveData<Coordinates>()

        @JvmStatic
        @Provides
        fun viewModel(
                fragment: QuakeMapFragment,
                factory: QuakeMap.ViewModel.Factory
        ) = ViewModelProviders.of(fragment, factory).get(QuakeMap.ViewModel::class.java)
    }
}