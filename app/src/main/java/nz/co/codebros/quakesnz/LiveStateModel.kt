package nz.co.codebros.quakesnz

import android.arch.lifecycle.LiveDataReactiveStreams
import ar.soflete.cycler.StateEventModel
import io.reactivex.BackpressureStrategy

/**
 * Created by Leandro on 18/02/2018.
 */
class LiveStateModel<S>(stateEventModel: StateEventModel<S, *>) {
    val liveData = LiveDataReactiveStreams.fromPublisher(
            stateEventModel.stateObservable.toFlowable(BackpressureStrategy.LATEST)
    )
}