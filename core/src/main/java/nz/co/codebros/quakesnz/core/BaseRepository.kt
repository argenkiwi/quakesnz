package nz.co.codebros.quakesnz.core

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.Subject

/**
 * Created by leandro on 24/06/17.
 */

abstract class BaseRepository<T> protected constructor(
        private val subject: Subject<T>
) : Repository<T> {
    override val observable: Observable<T>
        get() = subject
    override val observer: Observer<T>
        get() = subject
}
