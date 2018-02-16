package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeDetailViewModel @Inject constructor(
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
}