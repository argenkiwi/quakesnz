package nz.co.codebros.quakesnz.repository

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * Created by leandro on 24/06/17.
 */

interface Repository<T> {
    fun subscribe(consumer: Consumer<T>): Disposable

    fun publish(t: T)
}
