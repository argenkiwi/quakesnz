package nz.co.codebros.quakesnz.core

import android.os.Bundle
import android.os.Parcelable

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject

/**
 * Created by leandro on 24/06/17.
 */

abstract class BaseBundleRepository<T : Parcelable> protected constructor(
        private val subject: Subject<T>
) : BaseRepository<T>(subject), BundleRepository<T> {

    override fun publish(bundle: Bundle) {
        val t = bundle.getParcelable<T>(getKey())
        if (t != null) publish(t)
    }

    override fun subscribe(bundle: Bundle): Disposable =
            subject.subscribe { t -> bundle.putParcelable(getKey(), t) }

    protected abstract fun getKey() : String
}
