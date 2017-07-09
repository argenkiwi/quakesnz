package nz.co.codebros.quakesnz.core

import android.os.Bundle
import android.os.Parcelable

import io.reactivex.disposables.Disposable

/**
 * Created by leandro on 24/06/17.
 */

interface BundleRepository<T : Parcelable> : Repository<T> {
    fun subscribe(bundle: Bundle): Disposable
    fun publish(bundle: Bundle)
}
