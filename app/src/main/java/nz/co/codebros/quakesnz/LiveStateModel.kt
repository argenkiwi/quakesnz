package nz.co.codebros.quakesnz

import android.arch.lifecycle.LiveDataReactiveStreams
import ar.soflete.cycler.StateModel
import io.reactivex.BackpressureStrategy

/**
 * Created by Leandro on 18/02/2018.
 */
class LiveStateModel<S>(stateModel: StateModel<S>) {
    val liveData = LiveDataReactiveStreams.fromPublisher(
            stateModel.stateObservable.toFlowable(BackpressureStrategy.LATEST)
    )
}