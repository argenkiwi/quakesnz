package nz.co.codebros.quakesnz.repository

import android.os.Bundle
import android.os.Parcelable

import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.Subject

/**
 * Created by leandro on 24/06/17.
 */

abstract class BundleRepository<T : Parcelable> protected constructor(
        private val subject: Subject<T>
) : RepositoryImpl<T>(subject), IBundleRepository<T> {

    override fun publish(bundle: Bundle) {
        val t = bundle.getParcelable<T>(getKey())
        if (t != null) subject.onNext(t)
    }

    override fun subscribe(bundle: Bundle): Disposable {
        return subject.subscribe { t -> bundle.putParcelable(getKey(), t) }
    }

    protected abstract fun getKey() : String
}
