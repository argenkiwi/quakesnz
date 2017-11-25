package nz.co.codebros.quakesnz

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Leandro on 25/11/2017.
 */
abstract class ReducerViewModel<State, Event>(
        state: State,
        reducer: (State, Event) -> State
) : ViewModel() {
    val liveState: LiveData<State>
    val events: Observer<Event>
    protected val eventsObservable: Observable<Event>
    private val disposable: Disposable

    init {
        liveState = MutableLiveData<State>()
        events = PublishSubject.create()
        eventsObservable = events
        disposable = events.scan(state, reducer).subscribe({ liveState.value = it })
    }

    override fun onCleared() {
        super.onCleared()
        events.onComplete()
        disposable.dispose()
    }
}