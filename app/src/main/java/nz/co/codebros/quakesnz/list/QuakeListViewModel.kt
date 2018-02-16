package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import ar.soflete.cycler.EventModel
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import nz.co.codebros.quakesnz.error.ErrorEvent
import javax.inject.Inject

/**
 * Created by Leandro on 27/11/2017.
 */
class QuakeListViewModel @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        val quakeListModel: QuakeListModel
) : ViewModel() {

    private val errorModel = EventModel<ErrorEvent>()

    val stateLiveData = LiveDataReactiveStreams.fromPublisher(
            quakeListModel.stateObservable.toFlowable(BackpressureStrategy.LATEST)
    )

    val eventLiveData
        get() = LiveDataReactiveStreams.fromPublisher(
                errorModel.eventObservable.toFlowable(BackpressureStrategy.LATEST)
        )

    private val disposables = CompositeDisposable(
            quakeListModel,
            errorModel.publish(quakeListModel.eventObservable
                    .ofType<QuakeListEvent.LoadQuakesError>()
                    .map { ErrorEvent(it.error) })
    )

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
}