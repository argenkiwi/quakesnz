package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeMapViewModel @Inject constructor(
        quakeMapStateObservable: Observable<QuakeMapState>
) : ViewModel() {
    val quakeMapStateLiveData = LiveDataReactiveStreams.fromPublisher(
            quakeMapStateObservable.toFlowable(BackpressureStrategy.LATEST)
    )
}