package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import android.util.EventLog
import ar.soflete.cycler.EventModel
import io.reactivex.disposables.CompositeDisposable
import nz.co.codebros.quakesnz.error.ErrorEvent
import javax.inject.Inject

/**
 * Created by Leandro on 27/11/2017.
 */
class QuakeListViewModel(
        private val sharedPreferences: SharedPreferences,
        val quakeListModel: QuakeListModel,
        val errorModel: EventModel<ErrorEvent>
) : ViewModel() {

    private val disposables = CompositeDisposable().apply {
        add(quakeListModel)
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
            private val quakeListModel: QuakeListModel
    ) : ViewModelProvider.Factory {
        override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                QuakeListViewModel(sharedPreferences, quakeListModel, EventModel<ErrorEvent>()) as T
    }
}