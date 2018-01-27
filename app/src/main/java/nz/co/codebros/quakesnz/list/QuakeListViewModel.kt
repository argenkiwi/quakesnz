package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import io.reactivex.disposables.CompositeDisposable
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Leandro on 27/11/2017.
 */
class QuakeListViewModel(
        private val sharedPreferences: SharedPreferences,
        loadFeaturesInteractor: LoadFeaturesInteractor,
        selectFeatureInteractor: SelectFeatureInteractor,
        tracker: Tracker
) : ViewModel() {
    val liveQuakeListState: LiveData<QuakeListState>

    private val disposables = CompositeDisposable()
    private val quakeListModel = QuakeListModel()
    private val onSharePreferencesChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
        when (key) {
            "pref_intensity" -> quakeListModel.publish(QuakeListEvent.RefreshQuakes)
        }
    }

    init {
        // Bind state observable to live data
        val liveQuakeListState = MutableLiveData<QuakeListState>()
        disposables.add(quakeListModel.state.subscribe { liveQuakeListState.value = it })
        this.liveQuakeListState = liveQuakeListState

        // Bind side-effects to events
        disposables.addAll(
                quakeListModel.events
                        .startWith(QuakeListEvent.LoadQuakes())
                        .filter { it is QuakeListEvent.LoadQuakes }
                        .flatMap {
                            loadFeaturesInteractor.execute()
                                    .map { QuakeListEvent.QuakesLoaded(it.features) as QuakeListEvent }
                                    .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
                        }
                        .subscribe { quakeListModel.publish(it) },
                quakeListModel.events
                        .filter { it is QuakeListEvent.SelectQuake }
                        .map { (it as QuakeListEvent.SelectQuake).quake }
                        .subscribe { selectFeatureInteractor.execute(it) },
                quakeListModel.events.subscribe {
                    when (it) {
                        QuakeListEvent.RefreshQuakes -> tracker.send(HitBuilders.EventBuilder()
                                .setCategory("Interactions")
                                .setAction("Refresh")
                                .build())
                        is QuakeListEvent.SelectQuake -> tracker.send(HitBuilders.EventBuilder()
                                .setCategory("Interactions")
                                .setAction("Select quake")
                                .build())
                    }
                }
        )

        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharePreferencesChangeListener)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharePreferencesChangeListener)
    }

    fun onRefreshQuakes() {
        quakeListModel.publish(QuakeListEvent.RefreshQuakes)
    }

    fun onSelectQuake(feature: Feature) {
        quakeListModel.publish(QuakeListEvent.SelectQuake(feature))
    }

    class Factory @Inject constructor(
            private val sharedPreferences: SharedPreferences,
            private val interactor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor,
            @Named("app") private val tracker: Tracker
    ) : ViewModelProvider.Factory {
        override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                QuakeListViewModel(sharedPreferences, interactor, selectFeatureInteractor, tracker) as T
    }
}