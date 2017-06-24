package nz.co.codebros.quakesnz.repository

import android.os.Bundle
import android.os.Parcelable

import io.reactivex.disposables.Disposable

/**
 * Created by leandro on 24/06/17.
 */

interface IBundleRepository<T : Parcelable> : IRepository<T> {
    fun subscribe(bundle: Bundle): Disposable
    fun publish(bundle: Bundle)
}
