package nz.co.codebros.quakesnz.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment
import nz.co.codebros.quakesnz.detail.QuakeDetailModule
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.module.FeatureModule

/**
 * Created by leandro on 3/07/17.
 */
@Module
abstract class FeatureDetailActivityModule {

    @ContributesAndroidInjector(modules = arrayOf(QuakeDetailModule::class, FeatureModule::class))
    internal abstract fun quakeDetailFragment(): QuakeDetailFragment

    @ContributesAndroidInjector(modules = arrayOf(
            QuakeMapFragment.Module::class,
            FeatureModule::class
    ))
    internal abstract fun quakeMapFragment(): QuakeMapFragment
}
