package nz.co.codebros.quakesnz.list

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.plusAssign
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.core.extension.mapNotNull
import nz.co.codebros.quakesnz.core.extension.react
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.codebros.quakesnz.list.model.QuakeListReactor
import nz.co.codebros.quakesnz.list.model.QuakeListState
import nz.co.codebros.quakesnz.list.model.reduce
import nz.co.codebros.quakesnz.map.model.QuakeMapEvent
import nz.co.codebros.quakesnz.map.model.QuakeMapModel
import nz.co.codebros.quakesnz.ui.FeatureListActivity
import nz.co.codebros.quakesnz.util.changes
import javax.inject.Inject

@HiltViewModel
class QuakeListViewModel @Inject constructor(
        private val events: PublishProcessor<QuakeListEvent>,
        state: BehaviorProcessor<QuakeListState>,
        reactor: QuakeListReactor,
        sharedPreferences: SharedPreferences,
        quakeMapModel: QuakeMapModel
) : ViewModel() {

    val liveEvents
        get() = events.toLiveData()

    val liveState = state.toLiveData()

    private val disposables = CompositeDisposable()

    init {

        disposables += events.startWith(QuakeListEvent.LoadQuakes)
                .react(reactor::react)
                .subscribe(events::onNext)

        disposables += events.scan(QuakeListState(), ::reduce).subscribe(state::onNext)

        disposables += sharedPreferences.changes()
                .filter { it == "pref_intensity" }
                .map { QuakeListEvent.RefreshQuakes }
                .subscribe(events::onNext)

        disposables += state
                .mapNotNull { it.selectedFeature?.geometry?.coordinates }
                .distinct()
                .subscribe({
                    quakeMapModel.publish(QuakeMapEvent.OnNewCoordinates(it))
                }, {
                    Log.e(FeatureListActivity::class.simpleName, "Coordinates are null.", it)
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun refreshQuakes() {
        events.onNext(QuakeListEvent.RefreshQuakes)
    }

    fun selectQuake(feature: Feature) {
        events.onNext(QuakeListEvent.SelectQuake(feature))
    }
}
