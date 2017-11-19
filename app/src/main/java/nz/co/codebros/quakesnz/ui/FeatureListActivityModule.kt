package nz.co.codebros.quakesnz.ui

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.list.QuakeListFragment
import nz.co.codebros.quakesnz.list.QuakeListModule
import nz.co.codebros.quakesnz.map.QuakeMap
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.module.FeatureCollectionModule
import nz.co.codebros.quakesnz.module.FeatureModule
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository
import nz.co.codebros.quakesnz.repository.FeatureRepository

/**
 * Created by leandro on 3/07/17.
 */
@Module
abstract class FeatureListActivityModule {

    @Binds
    internal abstract fun onFeatureClickedListener(activity: FeatureListActivity)
            : QuakeListFragment.OnFeatureClickedListener

    @ContributesAndroidInjector(modules = arrayOf(
            QuakeListModule::class,
            FeatureCollectionModule::class,
            FeatureModule::class
    ))
    internal abstract fun quakeListFragment(): QuakeListFragment

    @ContributesAndroidInjector(modules = arrayOf(
            QuakeMap.Module::class,
            FeatureModule::class
    ))
    internal abstract fun quakeMapFragment(): QuakeMapFragment

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun featuresObservable(
                repository: FeatureCollectionRepository
        ): Observable<List<Feature>> = repository.observable.map { it.features }

        @JvmStatic
        @Provides
        fun featureObservable(
                repository: FeatureRepository
        ): Observable<Feature> = repository.observable
    }
}
