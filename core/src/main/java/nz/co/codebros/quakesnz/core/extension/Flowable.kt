package nz.co.codebros.quakesnz.core.extension

import io.reactivex.Flowable
import io.reactivex.Single

fun <T, R> Flowable<T>.react(mapper: (T) -> Single<R>?) = mapNotNull(mapper).flatMapSingle { it }

fun <T, R> Flowable<T>.mapNotNull(mapper: (T) -> R?) = map { Optional(mapper(it)) }
        .filter { it.value != null }
        .map { it.value }

private data class Optional<T>(val value: T?)
