package nz.co.codebros.quakesnz.ui

import android.arch.lifecycle.LiveDataReactiveStreams
import ar.soflete.daggerlifecycle.DaggerViewModel
import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.list.QuakeListModel
import javax.inject.Inject

/**
 * Created by Leandro on 23/11/2017.
 */
class FeatureListActivityViewModel @Inject constructor(
        private val quakeListModel: QuakeListModel
) : DaggerViewModel() {
    val eventLiveData
        get() = LiveDataReactiveStreams.fromPublisher(
                quakeListModel.eventObservable.toFlowable(BackpressureStrategy.LATEST)
        )
}