package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.BackpressureStrategy
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeDetailViewModel(
        private val quakeDetailModel: QuakeDetailModel
) : ViewModel() {

    val stateLiveData = LiveDataReactiveStreams.fromPublisher(
            quakeDetailModel.stateObservable.toFlowable(BackpressureStrategy.LATEST)
    )

    val eventLiveData
        get() = LiveDataReactiveStreams.fromPublisher(
                quakeDetailModel.eventObservable.toFlowable(BackpressureStrategy.LATEST)
        )

    override fun onCleared() {
        super.onCleared()
        quakeDetailModel.dispose()
    }

    internal class Factory @Inject constructor(
            private val model: QuakeDetailModel
    ) : ViewModelProvider.Factory {
        override fun <T : android.arch.lifecycle.ViewModel> create(modelClass: Class<T>) =
                QuakeDetailViewModel(model) as T
    }
}