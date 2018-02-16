package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.BackpressureStrategy
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeMapViewModel @Inject constructor(
        private val model: QuakeMapModel
) : ViewModel() {

    val stateLiveData = LiveDataReactiveStreams.fromPublisher(
            model.stateObservable.toFlowable(BackpressureStrategy.LATEST)
    )

    override fun onCleared() {
        super.onCleared()
        model.dispose()
    }

    class Factory @Inject constructor(
            private val model: QuakeMapModel
    ) : ViewModelProvider.Factory {
        override fun <T : android.arch.lifecycle.ViewModel?> create(modelClass: Class<T>) =
                QuakeMapViewModel(model) as T
    }
}