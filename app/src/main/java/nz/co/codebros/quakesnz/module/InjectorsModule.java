package nz.co.codebros.quakesnz.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment;
import nz.co.codebros.quakesnz.detail.QuakeDetailModule;
import nz.co.codebros.quakesnz.detail.QuakeMapFragment;
import nz.co.codebros.quakesnz.list.QuakeListFragment;
import nz.co.codebros.quakesnz.list.QuakeListModule;
import nz.co.codebros.quakesnz.settings.SettingsFragment;

/**
 * Created by leandro on 26/06/17.
 */
@Module
public abstract class InjectorsModule {
    @ContributesAndroidInjector(modules = {QuakeDetailModule.class, FeatureModule.class})
    abstract QuakeDetailFragment quakeDetailFragment();

    @ContributesAndroidInjector(modules = {QuakeListModule.class, FeatureCollectionModule.class})
    abstract QuakeListFragment quakeListFragment();

    @ContributesAndroidInjector(modules = FeatureModule.class)
    abstract QuakeMapFragment quakeMapFragment();

    @ContributesAndroidInjector(modules = FeatureCollectionModule.class)
    abstract SettingsFragment settingsFragment();
}
