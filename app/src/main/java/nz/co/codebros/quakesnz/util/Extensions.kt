package nz.co.codebros.quakesnz.util

import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

fun <T> Observable<T>.toLiveData(backpressureStrategy: BackpressureStrategy) =
        LiveDataReactiveStreams.fromPublisher(toFlowable(backpressureStrategy))