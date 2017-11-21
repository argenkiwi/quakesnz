package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import javax.inject.Inject


/**
 * Created by Leandro on 28/10/2017.
 */

class QuakeListViewModel(
        selectedFeatureObservable: Observable<Feature>,
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : ViewModel() {

    val state = MutableLiveData<State>()
    private val actions: Subject<Event> = PublishSubject.create()
    private val disposables = CompositeDisposable()

    init {
        disposables.add(actions
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
                        is Event.QuakeSelected -> {
                            state.copy(selectedFeature = action.quake)
                        }
                    }
                })
                .subscribe({ state.value = it }))

        actions.startWith(Event.LoadQuakes)
                .filter { it is Event.LoadQuakes }
                .flatMap {
                    loadFeaturesInteractor.execute()
                            .map { Event.QuakesLoaded(it.features) as Event }
                            .onErrorReturn { Event.LoadQuakesError(it) }
                }
                .subscribe(actions)

        selectedFeatureObservable
                .map { Event.QuakeSelected(it) }
                .subscribe(actions)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun onRefresh() {
        actions.onNext(Event.LoadQuakes)
    }

    fun onSelectFeature(feature: Feature) {
        selectFeatureInteractor.execute(feature)
    }

    data class State(
            val isLoading: Boolean,
            val features: List<Feature>? = null,
            val selectedFeature: Feature? = null,
            val error: Throwable? = null
    )

    sealed class Event {
        object LoadQuakes : Event()
        data class QuakesLoaded(val quakes: List<Feature>) : Event()
        data class QuakeSelected(val quake: Feature) : Event()
        data class LoadQuakesError(val error: Throwable) : Event()
    }

    class Factory @Inject constructor(
            private val selectedFeatureObservable: Observable<Feature>,
            private val interactor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = QuakeListViewModel(
                selectedFeatureObservable, interactor, selectFeatureInteractor
        ) as T
    }
}
