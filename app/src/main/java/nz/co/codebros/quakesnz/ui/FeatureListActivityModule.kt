package nz.co.codebros.quakesnz.ui

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import nz.co.codebros.quakesnz.list.QuakeListFragment
import nz.co.codebros.quakesnz.list.QuakeListModule
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.module.FeatureCollectionModule
import nz.co.codebros.quakesnz.module.FeatureModule

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

    @ContributesAndroidInjector(modules = arrayOf(FeatureModule::class))
    internal abstract fun quakeMapFragment(): QuakeMapFragment
}
