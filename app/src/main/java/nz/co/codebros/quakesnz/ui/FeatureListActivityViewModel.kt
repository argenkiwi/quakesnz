package nz.co.codebros.quakesnz.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject


/**
 * Created by Leandro on 23/11/2017.
 */
class FeatureListActivityViewModel(
        val dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>,
        val featureObservable: Observable<Feature>
) : ViewModel() {
    class Factory @Inject constructor(
            private val dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>,
            private val featureObservable: Observable<Feature>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) = FeatureListActivityViewModel(
                dispatchingSupportFragmentInjector,
                featureObservable
        ) as T
    }
}