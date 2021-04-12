package nz.co.codebros.quakesnz.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.plusAssign
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.core.extension.react
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.codebros.quakesnz.list.model.QuakeListReactor
import nz.co.codebros.quakesnz.list.model.QuakeListState
import javax.inject.Inject

@HiltViewModel
class QuakeListViewModel @Inject constructor(
        private val events: PublishProcessor<QuakeListEvent>,
        state: Flowable<QuakeListState>,
        reactor: QuakeListReactor
) : ViewModel() {

    val liveEvent
        get() = events.toLiveData()

    val liveState = state.toLiveData()

    private val disposables = CompositeDisposable()

    init {
        disposables += events.startWith(QuakeListEvent.LoadQuakes)
                .react(reactor::react)
                .subscribe(events::onNext)
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
