package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nz.co.codebros.quakesnz.core.data.Coordinates
import nz.co.codebros.quakesnz.repository.FeatureRepository
import javax.inject.Inject

/**
 * Created by Leandro on 20/11/2017.
 */
internal interface QuakeMap {

    class ViewModel(
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