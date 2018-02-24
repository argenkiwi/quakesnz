package nz.co.codebros.quakesnz.util

import android.arch.lifecycle.LiveDataReactiveStreams
import android.content.SharedPreferences
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

fun <T> Observable<T>.toLiveData(backpressureStrategy: BackpressureStrategy) =
        LiveDataReactiveStreams.fromPublisher(toFlowable(backpressureStrategy))

fun SharedPreferences.changes(): Observable<String> = Observable.create<String>({ emitter ->
    SharedPreferences.OnSharedPreferenceChangeListener { _, key -> emitter.onNext(key) }.let {
        emitter.setCancellable { unregisterOnSharedPreferenceChangeListener(it) }
        registerOnSharedPreferenceChangeListener(it)
    }
})
