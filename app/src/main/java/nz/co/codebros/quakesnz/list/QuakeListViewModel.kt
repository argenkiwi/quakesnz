package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
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
class QuakeListViewModel(
        private val sharedPreferences: SharedPreferences,
        val quakeListModel: QuakeListModel,
        val errorModel: EventModel<ErrorEvent>
) : ViewModel() {

    val stateLiveData = LiveDataReactiveStreams.fromPublisher(
            quakeListModel.stateObservable.toFlowable(BackpressureStrategy.LATEST)
    )

    val eventLiveData = LiveDataReactiveStreams.fromPublisher(
            errorModel.eventObservable.toFlowable(BackpressureStrategy.LATEST)
    )

    private val disposables = CompositeDisposable().apply {
        add(quakeListModel)
        add(errorModel.publish(quakeListModel.eventObservable
                .ofType<QuakeListEvent.LoadQuakesError>()
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