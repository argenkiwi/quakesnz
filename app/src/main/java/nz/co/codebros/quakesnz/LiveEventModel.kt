package nz.co.codebros.quakesnz

import android.arch.lifecycle.LiveDataReactiveStreams
import ar.soflete.cycler.EventModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

/**
 * Created by Leandro on 18/02/2018.
 */
open class LiveEventModel<M>(private val eventModel: EventModel<M>) {
    val liveData
        get() = LiveDataReactiveStreams.fromPublisher(
                eventModel.eventObservable.toFlowable(BackpressureStrategy.LATEST)
        )

    fun publish(event: M) {
        eventModel.publish(event)
    }

    fun publish(eventObservable: Observable<M>) = eventModel.publish(eventObservable)
}