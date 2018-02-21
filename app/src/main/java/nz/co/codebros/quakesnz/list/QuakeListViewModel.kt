package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.util.toLiveData
import javax.inject.Inject

/**
 * Created by Leandro on 27/11/2017.
 */
class QuakeListViewModel @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val quakeListModel: QuakeListModel
) : ViewModel() {

    val quakeListState = quakeListModel.stateObservable.toLiveData(BackpressureStrategy.LATEST)
    val quakeListEvents
        get() = quakeListModel.eventObservable.toLiveData(BackpressureStrategy.LATEST)

    private val preferencesChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            "pref_intensity" -> quakeListModel.publish(QuakeListEvent.RefreshQuakes)
        }
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferencesChangeListener)
    }

    override fun onCleared() {
        super.onCleared()
        quakeListModel.dispose()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferencesChangeListener)
    }

    fun refreshQuakes() {
        quakeListModel.publish(QuakeListEvent.RefreshQuakes)
    }

    fun selectQuake(feature: Feature) {
        quakeListModel.publish(QuakeListEvent.SelectQuake(feature))
    }
}