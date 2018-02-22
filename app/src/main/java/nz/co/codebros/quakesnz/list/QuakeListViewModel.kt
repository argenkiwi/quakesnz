package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by Leandro on 27/11/2017.
 */
class QuakeListViewModel @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        val quakeListModel: QuakeListModel
) : ViewModel() {

    private val preferencesChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            "pref_intensity" -> quakeListModel.publish(QuakeListEvent.RefreshQuakes)
        }
    }

    private val disposable = quakeListModel.subscribe()

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferencesChangeListener)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferencesChangeListener)
    }

    fun refreshQuakes() {
        quakeListModel.publish(QuakeListEvent.RefreshQuakes)
    }

    fun selectQuake(feature: Feature) {
        quakeListModel.publish(QuakeListEvent.SelectQuake(feature))
    }
}