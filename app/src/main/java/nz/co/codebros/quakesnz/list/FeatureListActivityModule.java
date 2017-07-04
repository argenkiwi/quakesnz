package nz.co.codebros.quakesnz.list;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nz.co.codebros.quakesnz.detail.QuakeMapFragment;
import nz.co.codebros.quakesnz.module.FeatureCollectionModule;
import nz.co.codebros.quakesnz.module.FeatureModule;

/**
 * Created by leandro on 3/07/17.
 */
@Module
public abstract class FeatureListActivityModule {

    @Binds
    abstract QuakeListFragment.OnFeatureClickedListener onFeatureClickedListener(FeatureListActivity activit);

    @ContributesAndroidInjector(modules = {
            QuakeListModule.class,
            FeatureCollectionModule.class,
            FeatureModule.class
    })
    abstract QuakeListFragment quakeListFragment();

    @ContributesAndroidInjector(modules = FeatureModule.class)
    abstract QuakeMapFragment quakeMapFragment();
}
