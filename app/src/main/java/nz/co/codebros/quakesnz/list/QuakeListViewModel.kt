package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
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
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : ViewModel() {

    val state = MutableLiveData<State>()
    private val eventsObserver: Observer<Event>
    private val disposables = CompositeDisposable()

    init {
        val eventsSubject = PublishSubject.create<Event>()
        val eventsObservable = eventsSubject.startWith(Event.LoadQuakes())

        eventsObserver = eventsSubject

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

        disposables.add(eventsObservable
                .scan(State(false), { state, action ->
                    when (action) {
                        is Event.LoadQuakes -> {
                            state.copy(isLoading = true)
                        }
                        is Event.LoadQuakesError -> {
                            state.copy(isLoading = false, error = action.error)
                        }
                        is Event.QuakesLoaded -> {
                            state.copy(isLoading = false, features = action.quakes)
                        }
                        is Event.SelectQuake -> {
                            state.copy(selectedFeature = action.quake)
                        }
                    }
                })
                .subscribe({ state.value = it }))

        eventsObservable.filter { it is Event.LoadQuakes }
                .flatMap {
                    loadFeaturesInteractor.execute()
                            .map { Event.QuakesLoaded(it.features) as Event }
                            .onErrorReturn { Event.LoadQuakesError(it) }
                }
                .subscribe(eventsObserver)

        disposables.add(eventsObservable.filter { it is Event.SelectQuake }
                .map { (it as Event.SelectQuake).quake }
                .subscribe({ selectFeatureInteractor.execute(it) }))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        eventsObserver.onComplete()
    }

    fun onRefresh() {
        eventsObserver.onNext(Event.RefreshQuakes)
    }

    fun onSelectFeature(feature: Feature) {
        eventsObserver.onNext(Event.SelectQuake(feature))
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
            private val interactor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = QuakeListViewModel(
                tracker, interactor, selectFeatureInteractor
        ) as T
    }
}
