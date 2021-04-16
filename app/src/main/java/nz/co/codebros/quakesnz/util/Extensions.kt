package nz.co.codebros.quakesnz.util

import android.content.SharedPreferences
import androidx.lifecycle.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

fun <T> Observable<T>.toLiveData(backpressureStrategy: BackpressureStrategy) =
        toFlowable(backpressureStrategy).toLiveData()

fun <T, S> Observable<T>.mapNotNull(mapper: (T) -> S?): Observable<S> = map { Optional(mapper(it)) }
        .filter { it.value != null }
        .map { it.value }

data class Optional<T>(val value: T?)

fun SharedPreferences.changes() = Observable.create<String> { emitter ->
    SharedPreferences.OnSharedPreferenceChangeListener { _, key -> emitter.onNext(key) }.let {
        emitter.setCancellable { unregisterOnSharedPreferenceChangeListener(it) }
        registerOnSharedPreferenceChangeListener(it)
    }
}
