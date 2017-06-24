package nz.co.codebros.quakesnz.repository

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.Subject

/**
 * Created by leandro on 24/06/17.
 */

abstract class BaseRepository<T> protected constructor(
        private val subject: Subject<T>
) : Repository<T> {
    override fun subscribe(consumer: Consumer<T>): Disposable {
        return subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)
    }

    override fun publish(t: T) {
        subject.onNext(t)
    }
}
