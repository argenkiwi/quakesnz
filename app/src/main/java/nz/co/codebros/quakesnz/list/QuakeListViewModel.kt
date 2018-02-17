package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import ar.soflete.cycler.EventModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import nz.co.codebros.quakesnz.LiveEventModel
import nz.co.codebros.quakesnz.LiveStateModel
import nz.co.codebros.quakesnz.error.ErrorEvent
import javax.inject.Inject

/**
 * Created by Leandro on 27/11/2017.
 */
class QuakeListViewModel @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        quakeListModel: QuakeListModel
) : ViewModel() {

    val quakeListState = LiveStateModel(quakeListModel)
    val quakeListEvents = LiveEventModel(quakeListModel)
    val errorEvents: LiveEventModel<ErrorEvent>

    private val disposables = CompositeDisposable(quakeListModel)
    private val onSharePreferencesChangeListener = SharedPreferences
            .OnSharedPreferenceChangeListener { _, key ->
                when (key) {
                    "pref_intensity" -> quakeListModel.publish(QuakeListEvent.RefreshQuakes)
                }
            }

    init {
        val errorModel = EventModel<ErrorEvent>()
        this.errorEvents = LiveEventModel(errorModel)
        disposables.add(errorModel
                .publish(quakeListModel.eventObservable
                        .ofType<QuakeListEvent.LoadQuakesError>()
                        .map { ErrorEvent(it.error) }))
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharePreferencesChangeListener)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharePreferencesChangeListener)
    }
}