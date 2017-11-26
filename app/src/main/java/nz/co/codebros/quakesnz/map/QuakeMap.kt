package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import ar.soflete.cycler.ReactiveViewModel
import dagger.Provides
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Coordinates
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by Leandro on 20/11/2017.
 */
interface QuakeMap {

    data class State(val coordinates: Coordinates? = null)

    class ViewModel(
            featureObservable: Observable<Feature>
    ) : ReactiveViewModel<State, Coordinates>(State(), QuakeMapReducer) {
        init {
            subscribe(featureObservable.map { it.geometry.coordinates })
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