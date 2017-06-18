package nz.co.codebros.quakesnz.repository

import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by leandro on 18/06/17.
 */

abstract class Repository<T>(private val observer: Observer<T>) {
    protected fun load(single: Single<T>): Completable {
        return single
                .doAfterSuccess { observer.onNext(it) }
                .toCompletable()
    }
}
