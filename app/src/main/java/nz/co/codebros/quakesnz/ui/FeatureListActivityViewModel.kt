package nz.co.codebros.quakesnz.ui

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.list.QuakeListModel
import javax.inject.Inject

/**
 * Created by Leandro on 23/11/2017.
 */
class FeatureListActivityViewModel(
        val dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>,
        private val quakeListModel: QuakeListModel
) : ViewModel() {

    val eventLiveData
        get() = LiveDataReactiveStreams.fromPublisher(
                quakeListModel.eventObservable.toFlowable(BackpressureStrategy.LATEST)
        )

    class Factory @Inject constructor(
            private val dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>,
            private val quakeListModel: QuakeListModel
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) = FeatureListActivityViewModel(
                dispatchingSupportFragmentInjector, quakeListModel
        ) as T
    }
}