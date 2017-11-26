package ar.soflete.cycler

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Leandro on 26/11/2017.
 */
abstract class ReactiveViewModel<State, Event>(
        state: State,
        reducer: Reducer<State, Event>
) : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _events: Subject<Event> = PublishSubject.create()
    protected val events: Observable<Event> = _events

    private val _disposables = CompositeDisposable()

    init {
        collect(_events.scan(state, reducer::reduce).subscribe({ _state.value = it }))
    }

    fun publish(event: Event) {
        _events.onNext(event)
    }

    fun subscribe(observable: Observable<Event>) {
        observable.subscribe(_events)
    }

    protected fun collect(disposable: Disposable) {
        _disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        _events.onComplete()
        _disposables.dispose()
    }
}