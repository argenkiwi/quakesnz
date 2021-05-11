package nz.co.codebros.quakesnz.core.extension

import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nz.co.codebros.quakesnz.core.extension.mapNotNull

fun <T, R> Flowable<T>.react(mapper: (T) -> Single<R>?): Flowable<R> {
        return mapNotNull(mapper).flatMapSingle { it }
}

fun <T, R> Flowable<T>.mapNotNull(mapper: (T) -> R?) = map { Optional(mapper(it)) }
        .filter { it.value != null }
        .map { it.value }

private data class Optional<T>(val value: T?)
