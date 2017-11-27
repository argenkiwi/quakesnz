package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import ar.soflete.cycler.ReactiveViewModel
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import io.reactivex.disposables.CompositeDisposable
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Leandro on 27/11/2017.
 */
class QuakeListViewModel(
        tracker: Tracker,
        private val sharedPreferences: SharedPreferences,
        private val loadFeaturesInteractor: LoadFeaturesInteractor,
        private val selectFeatureInteractor: SelectFeatureInteractor
) : ReactiveViewModel<QuakeListState, QuakeListEvent>(
        QuakeListState(false), QuakeListReducer::reduce
), SharedPreferences.OnSharedPreferenceChangeListener {
    private val disposables = CompositeDisposable()

    init {
        subscribe(events
                .startWith(QuakeListEvent.LoadQuakes())
                .filter { it is QuakeListEvent.LoadQuakes }
                .flatMap {
                    loadFeaturesInteractor.execute()
                            .map { QuakeListEvent.QuakesLoaded(it.features) as QuakeListEvent }
                            .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
                })

        disposables.add(events
                .filter { it is QuakeListEvent.SelectQuake }
                .map { (it as QuakeListEvent.SelectQuake).quake }
                .subscribe({ selectFeatureInteractor.execute(it) }))

        disposables.add(events.subscribe({
            when (it) {
                is QuakeListEvent.RefreshQuakes -> tracker.send(HitBuilders.EventBuilder()
                        .setCategory("Interactions")
                        .setAction("Refresh")
                        .build())
                is QuakeListEvent.SelectQuake -> tracker.send(HitBuilders.EventBuilder()
                        .setCategory("Interactions")
                        .setAction("Select quake")
                        .build())
            }
        }))

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        when (key) {
            "pref_intensity" -> publish(QuakeListEvent.LoadQuakes())
        }
    }

    class Factory @Inject constructor(
            @Named("app") private val tracker: Tracker,
            private val sharedPreferences: SharedPreferences,
            private val interactor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor
    ) : ViewModelProvider.Factory {
        override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                QuakeListViewModel(tracker, sharedPreferences, interactor, selectFeatureInteractor) as T
    }
}