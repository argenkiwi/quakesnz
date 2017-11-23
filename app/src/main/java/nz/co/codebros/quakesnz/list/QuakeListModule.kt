package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.scope.FragmentScope
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractorImpl
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractorImpl

/**
 * Created by Leandro on 2/11/2017.
 */
@Module
abstract class QuakeListModule {

    @Binds
    abstract fun eventObserver(
            eventSubject: Subject<QuakeListViewModel.Event>
    ): Observer<QuakeListViewModel.Event>

    @Binds
    abstract fun eventObservable(
            eventSubject: Subject<QuakeListViewModel.Event>
    ): Observable<QuakeListViewModel.Event>

    @Binds
    abstract fun loadFeaturesInteractor(
            loadFeaturesInteractorImpl: LoadFeaturesInteractorImpl
    ): LoadFeaturesInteractor

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun mutableState() = MutableLiveData<QuakeListViewModel.State>()

        @JvmStatic
        @Provides
        @FragmentScope
        internal fun eventSubject(): Subject<QuakeListViewModel.Event> =
                PublishSubject.create<QuakeListViewModel.Event>()

        @JvmStatic
        @Provides
        internal fun selectFeatureInteractor(
                featureSubject: Subject<Feature>
        ): SelectFeatureInteractor = SelectFeatureInteractorImpl(featureSubject)

        @JvmStatic
        @Provides
        internal fun viewModel(
                fragment: QuakeListFragment,
                factory: QuakeListViewModel.Factory
        ) = ViewModelProviders.of(fragment, factory).get(QuakeListViewModel::class.java)
    }
}
