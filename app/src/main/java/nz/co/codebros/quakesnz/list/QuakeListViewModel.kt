package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Leandro on 28/10/2017.
 */

class QuakeListViewModel(
        tracker: Tracker,
        private val sharedPreferences: SharedPreferences,
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor,
        mutableState: MutableLiveData<State>,
        private val eventObserver: Observer<Event>,
        eventObservable: Observable<Event>
) : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {
    val liveState: LiveData<State> = mutableState

    private val disposables = CompositeDisposable()

    private val reducer: (State, Event) -> State = { state, event ->
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

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        val eventsObservable = eventObservable.startWith(Event.LoadQuakes())

        disposables.add(eventsObservable
                .scan(State(false), reducer)
                .subscribe({ mutableState.value = it }))

        eventsObservable.filter { it is Event.LoadQuakes }
                .flatMap {
                    loadFeaturesInteractor.execute()
                            .map { Event.QuakesLoaded(it.features) as Event }
                            .onErrorReturn { Event.LoadQuakesError(it) }
                }
                .subscribe(eventObserver)

        disposables.add(eventsObservable.filter { it is Event.SelectQuake }
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
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        eventObserver.onComplete()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    fun onRefresh() {
        eventObserver.onNext(Event.RefreshQuakes)
    }

    fun onSelectFeature(feature: Feature) {
        eventObserver.onNext(Event.SelectQuake(feature))
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        when (key) {
            "pref_intensity" -> eventObserver.onNext(Event.RefreshQuakes)
        }
    }

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

    class Factory @Inject constructor(
            @Named("app") private val tracker: Tracker,
            private val sharedPreferences: SharedPreferences,
            private val interactor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor,
            private val mutableState: MutableLiveData<State>,
            private val eventObserver: Observer<Event>,
            private val eventObservable: Observable<Event>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = QuakeListViewModel(
                tracker, sharedPreferences, interactor, selectFeatureInteractor, mutableState, eventObserver, eventObservable
        ) as T
    }
}
