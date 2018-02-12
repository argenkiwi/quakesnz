package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import ar.soflete.cycler.EventModel
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import io.reactivex.disposables.CompositeDisposable
import nz.co.codebros.quakesnz.error.ErrorEvent
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
        @Named("app") tracker: Tracker
) : ViewModel() {
    val quakeListModel = QuakeListModel(QuakeListState(false))
    val errorModel = EventModel<ErrorEvent>()

    private val disposables = CompositeDisposable().apply {
        add(quakeListModel.publish(quakeListModel.eventObservable
                .startWith(QuakeListEvent.LoadQuakes())
                .filter { it is QuakeListEvent.LoadQuakes }
                .flatMap {
                    loadFeaturesInteractor.execute()
                            .map { QuakeListEvent.QuakesLoaded(it.features) as QuakeListEvent }
                            .onErrorReturn { QuakeListEvent.LoadQuakesError(it) }
                }))

        add(quakeListModel.eventObservable
                .filter { it is QuakeListEvent.SelectQuake }
                .map { (it as QuakeListEvent.SelectQuake).quake }
                .subscribe { selectFeatureInteractor.execute(it) })

        add(quakeListModel.eventObservable.subscribe {
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

        add(errorModel.publish(quakeListModel.eventObservable
                .filter { it is QuakeListEvent.LoadQuakesError }
                .map { it as QuakeListEvent.LoadQuakesError }
                .map { ErrorEvent(it.error) }))
    }

    private val onSharePreferencesChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            "pref_intensity" -> quakeListModel.publish(QuakeListEvent.RefreshQuakes)
        }
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharePreferencesChangeListener)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharePreferencesChangeListener)
    }

    class Factory @Inject constructor(
            private val sharedPreferences: SharedPreferences,
            private val loadFeaturesInteractor: LoadFeaturesInteractor,
            private val selectFeatureInteractor: SelectFeatureInteractor,
            @Named("app") private val tracker: Tracker
    ) : ViewModelProvider.Factory {
        override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                QuakeListViewModel(sharedPreferences, loadFeaturesInteractor, selectFeatureInteractor, tracker) as T
    }
}