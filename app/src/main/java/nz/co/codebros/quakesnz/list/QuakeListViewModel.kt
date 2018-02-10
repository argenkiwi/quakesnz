package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import ar.soflete.cycler.EventModel
import nz.co.codebros.quakesnz.error.ErrorEvent
import javax.inject.Inject

/**
 * Created by Leandro on 27/11/2017.
 */
class QuakeListViewModel(
        private val sharedPreferences: SharedPreferences,
        val quakeListModel: QuakeListModel
) : ViewModel() {
    private val onSharePreferencesChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            "pref_intensity" -> quakeListModel.publish(QuakeListEvent.RefreshQuakes)
        }
    }

    val errorModel = EventModel<ErrorEvent>()

    init {
        errorModel.publish(quakeListModel.eventObservable
                .filter { it is QuakeListEvent.LoadQuakesError }
                .map { it as QuakeListEvent.LoadQuakesError }
                .map { ErrorEvent(it.error) })

        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharePreferencesChangeListener)
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharePreferencesChangeListener)
    }

    class Factory @Inject constructor(
            private val sharedPreferences: SharedPreferences,
            private val quakeListModel: QuakeListModel
    ) : ViewModelProvider.Factory {
        override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                QuakeListViewModel(sharedPreferences, quakeListModel) as T
    }
}