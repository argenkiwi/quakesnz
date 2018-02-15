package nz.co.codebros.quakesnz.ui

import android.arch.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractorImpl
import nz.co.codebros.quakesnz.list.QuakeListFragment
import nz.co.codebros.quakesnz.list.QuakeListModel
import nz.co.codebros.quakesnz.list.QuakeListModule
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.map.QuakeMapModule
import nz.co.codebros.quakesnz.scope.FragmentScope

/**
 * Created by leandro on 3/07/17.
 */
@Module
abstract class FeatureListActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [QuakeListModule::class])
    internal abstract fun quakeListFragment(): QuakeListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [QuakeMapModule::class])
    internal abstract fun quakeMapFragment(): QuakeMapFragment

    @Binds
    internal abstract fun loadFeaturesInteractor(
            loadFeaturesInteractorImpl: LoadFeaturesInteractorImpl
    ): LoadFeaturesInteractor

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun featureObservable(
                quakeListModel: QuakeListModel
        ): Observable<Feature> = quakeListModel.stateObservable.map { it.selectedFeature }

        @JvmStatic
        @Provides
        fun viewModel(
                activity: FeatureListActivity,
                factory: FeatureListActivityViewModel.Factory
        ) = ViewModelProviders.of(activity, factory).get(FeatureListActivityViewModel::class.java)
    }
}
