package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import dagger.Binds
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.ReducerViewModel
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractorImpl
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractorImpl
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Leandro on 25/11/2017.
 */
interface QuakeList {

    data class State(
            val isLoading: Boolean,
            val features: List<Feature>? = null,
            val selectedFeature: Feature? = null,
            val error: Throwable? = null
    )

    sealed class Event {
        open class LoadQuakes : Event()
        object RefreshQuakes : LoadQuakes()
        data class QuakesLoaded(val quakes: List<Feature>) : Event()
        data class SelectQuake(val quake: Feature) : Event()
        data class LoadQuakesError(val error: Throwable) : Event()
    }

    class ViewModel(
            tracker: Tracker,
            private val sharedPreferences: SharedPreferences,
            private val loadFeaturesInteractor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor
    ) : ReducerViewModel<State, Event>(
            QuakeList.State(false),
            { state, event ->
                when (event) {
                    is Event.LoadQuakes -> {
                        state.copy(isLoading = true)
                    }
                    is Event.LoadQuakesError -> {
                        state.copy(isLoading = false, error = event.error)
                    }
                    is Event.QuakesLoaded -> {
                        state.copy(isLoading = false, features = event.quakes)
                    }
                    is Event.SelectQuake -> {
                        state.copy(selectedFeature = event.quake)
                    }
                }
            }
    ) {

        private val disposables = CompositeDisposable()
        private val preferenceChangeListener: (SharedPreferences, String) -> Unit = { _, key ->
            when (key) {
                "pref_intensity" -> events.onNext(Event.LoadQuakes())
            }
        }

        init {
            eventsObservable
                    .startWith(Event.LoadQuakes())
                    .filter { it is Event.LoadQuakes }
                    .flatMap {
                        loadFeaturesInteractor.execute()
                                .map { Event.QuakesLoaded(it.features) as Event }
                                .onErrorReturn { Event.LoadQuakesError(it) }
                    }
                    .subscribe(events)

            disposables.add(eventsObservable
                    .filter { it is Event.SelectQuake }
                    .map { (it as Event.SelectQuake).quake }
                    .subscribe({ selectFeatureInteractor.execute(it) }))

            disposables.add(eventsObservable
                    .subscribe({
                        when (it) {
                            is Event.RefreshQuakes -> tracker.send(HitBuilders.EventBuilder()
                                    .setCategory("Interactions")
                                    .setAction("Refresh")
                                    .build())
                            is Event.SelectQuake -> tracker.send(HitBuilders.EventBuilder()
                                    .setCategory("Interactions")
                                    .setAction("Select quake")
                                    .build())
                        }
                    }))

            sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        }

        override fun onCleared() {
            super.onCleared()
            disposables.dispose()
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        }

        class Factory @Inject constructor(
                @Named("app") private val tracker: Tracker,
                private val sharedPreferences: SharedPreferences,
                private val interactor: LoadFeaturesInteractor,
                private val selectFeatureInteractor: SelectFeatureInteractor
        ) : ViewModelProvider.Factory {
            override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                    ViewModel(tracker, sharedPreferences, interactor, selectFeatureInteractor) as T
        }
    }

    @dagger.Module
    abstract class Module {

        @Binds
        abstract fun loadFeaturesInteractor(
                loadFeaturesInteractorImpl: LoadFeaturesInteractorImpl
        ): LoadFeaturesInteractor

        @dagger.Module
        companion object {

            @JvmStatic
            @Provides
            internal fun selectFeatureInteractor(
                    featureSubject: Subject<Feature>
            ): SelectFeatureInteractor = SelectFeatureInteractorImpl(featureSubject)

            @JvmStatic
            @Provides
            internal fun viewModel(
                    fragment: QuakeListFragment,
                    factory: QuakeList.ViewModel.Factory
            ) = ViewModelProviders.of(fragment, factory).get(QuakeList.ViewModel::class.java)
        }
    }
}