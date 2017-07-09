package nz.co.codebros.quakesnz.ui;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nz.co.codebros.quakesnz.map.QuakeMapFragment;
import nz.co.codebros.quakesnz.list.QuakeListFragment;
import nz.co.codebros.quakesnz.list.QuakeListModule;
import nz.co.codebros.quakesnz.module.FeatureCollectionModule;
import nz.co.codebros.quakesnz.module.FeatureModule;

/**
 * Created by leandro on 3/07/17.
 */
@Module
public abstract class FeatureListActivityModule {

    @Binds
    abstract QuakeListFragment.OnFeatureClickedListener onFeatureClickedListener(FeatureListActivity activity);

    @ContributesAndroidInjector(modules = {
            QuakeListModule.class,
            FeatureCollectionModule.class,
            FeatureModule.class
    })
    abstract QuakeListFragment quakeListFragment();

    @ContributesAndroidInjector(modules = FeatureModule.class)
    abstract QuakeMapFragment quakeMapFragment();
}
