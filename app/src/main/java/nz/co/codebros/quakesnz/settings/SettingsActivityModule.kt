package nz.co.codebros.quakesnz.settings

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nz.co.codebros.quakesnz.module.FeatureCollectionModule
import nz.co.codebros.quakesnz.settings.SettingsFragment

/**
 * Created by leandro on 3/07/17.
 */
@Module
abstract class SettingsActivityModule {

    @ContributesAndroidInjector(modules = arrayOf(SettingsModule::class, FeatureCollectionModule::class))
    internal abstract fun settingsFragment(): SettingsFragment
}
