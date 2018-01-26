package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
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
        val quakeListModel: QuakeListModel,
        private val sharedPreferences: SharedPreferences,
        loadFeaturesInteractor: LoadFeaturesInteractor,
        selectFeatureInteractor: SelectFeatureInteractor,
        tracker: Tracker
) : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val disposables = CompositeDisposable()

    init {
        disposables.addAll(
                quakeListModel.init(QuakeListState(false)),
                quakeListModel.events
                        .startWith(QuakeListEvent.LoadQuakes())
                        .filter { it is QuakeListEvent.LoadQuakes }
                        .flatMap {
                            loadFeaturesInteractor.execute()
                                    .map { QuakeListEvent.QuakesLoaded(it.features) as QuakeListEvent }
                                    .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
                        }
                        .subscribe { quakeListModel.events.onNext(it) },
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
                })

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        when (key) {
            "pref_intensity" -> quakeListModel.events.onNext(QuakeListEvent.LoadQuakes())
        }
    }

    class Factory @Inject constructor(
            private val sharedPreferences: SharedPreferences,
            private val interactor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor,
            @Named("app") private val tracker: Tracker
    ) : ViewModelProvider.Factory {
        override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                QuakeListViewModel(QuakeListModel, sharedPreferences, interactor, selectFeatureInteractor, tracker) as T
    }
}