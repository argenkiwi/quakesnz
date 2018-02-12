package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import ar.soflete.cycler.Reducer
import ar.soflete.cycler.StateEventModel
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

    class ViewModel(featureObservable: Observable<Feature>) : android.arch.lifecycle.ViewModel() {

        val model = StateEventModel<State, Coordinates>(State(), Companion)

        private val disposable = model.publish(featureObservable.map { it.geometry.coordinates })

        override fun onCleared() {
            super.onCleared()
            disposable.dispose()
        }

        class Factory @Inject constructor(
                private val featureObservable: Observable<Feature>
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel?> create(modelClass: Class<T>) =
                    ViewModel(featureObservable) as T
        }

        companion object : Reducer<State, Coordinates> {
            override fun apply(state: State, event: Coordinates) = state.copy(coordinates = event)
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