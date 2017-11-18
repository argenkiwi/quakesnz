package nz.co.codebros.quakesnz.core

import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by leandro on 24/06/17.
 */

interface Repository<T> {
    val observer: Observer<T>
    val observable: Observable<T>
}
