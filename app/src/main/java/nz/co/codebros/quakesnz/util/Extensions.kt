package nz.co.codebros.quakesnz.util

import android.content.SharedPreferences
import androidx.lifecycle.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

fun <T> Observable<T>.toLiveData(backpressureStrategy: BackpressureStrategy) =
        toFlowable(backpressureStrategy).toLiveData()

fun SharedPreferences.changes() = Observable.create<String> { emitter ->
    SharedPreferences.OnSharedPreferenceChangeListener { _, key -> emitter.onNext(key) }.let {
        emitter.setCancellable { unregisterOnSharedPreferenceChangeListener(it) }
        registerOnSharedPreferenceChangeListener(it)
    }
}
