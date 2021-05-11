package nz.co.codebros.quakesnz.util

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow

fun <T> Observable<T>.toLiveData(backpressureStrategy: BackpressureStrategy) =
    toFlowable(backpressureStrategy).toLiveData()

fun <T, S> Observable<T>.mapNotNull(mapper: (T) -> S?): Observable<S> = map { Optional(mapper(it)) }
    .filter { it.value != null }
    .map { it.value }

data class Optional<T>(val value: T?)

fun SharedPreferences.changesFlowable() = Flowable.create<String>({ emitter ->
    SharedPreferences.OnSharedPreferenceChangeListener { _, key -> emitter.onNext(key) }.let {
        emitter.setCancellable { unregisterOnSharedPreferenceChangeListener(it) }
        registerOnSharedPreferenceChangeListener(it)
    }
}, BackpressureStrategy.BUFFER)

@ExperimentalCoroutinesApi
fun SharedPreferences.changesFlow() = callbackFlow<String> {

    val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        try {
            sendBlocking(key)
        } catch (t: Throwable) {
            Log.e(SharedPreferences::class.simpleName, "Failed to send event for $key", t)
        }
    }

    registerOnSharedPreferenceChangeListener(listener)

    awaitClose { unregisterOnSharedPreferenceChangeListener(listener) }
}
