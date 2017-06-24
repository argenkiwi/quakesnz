package nz.co.codebros.quakesnz.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by leandro on 24/06/17.
 */

abstract class RepositoryImpl<T> protected constructor(
        private val observable: Observable<T>
) : IRepository<T> {
    override fun subscribe(consumer: Consumer<T>): Disposable {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)
    }
}
