package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import dagger.Provides
import io.reactivex.Observable
import nz.co.codebros.quakesnz.ReducerViewModel
import nz.co.codebros.quakesnz.core.data.Coordinates
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by Leandro on 20/11/2017.
 */
internal interface QuakeMap {

    data class State(val coordinates: Coordinates? = null)

    class ViewModel(
            featureObservable: Observable<Feature>
    ) : ReducerViewModel<State, Coordinates>(State(), { state, coordinates ->
        state.copy(coordinates = coordinates)
    }) {
        init {
            featureObservable
                    .map { it.geometry.coordinates }
                    .subscribe(events)
        }

        class Factory @Inject constructor(
                private val featureObservable: Observable<Feature>
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel?> create(modelClass: Class<T>) =
                    ViewModel(featureObservable) as T
        }
    }

    @dagger.Module
    object Module {

        @JvmStatic
        @Provides
        fun viewModel(
                fragment: QuakeMapFragment,
                factory: QuakeMap.ViewModel.Factory
        ) = ViewModelProviders.of(fragment, factory).get(QuakeMap.ViewModel::class.java)
    }
}