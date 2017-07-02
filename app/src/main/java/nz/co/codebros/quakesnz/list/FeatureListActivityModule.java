package nz.co.codebros.quakesnz.list;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nz.co.codebros.quakesnz.detail.QuakeMapFragment;
import nz.co.codebros.quakesnz.list.QuakeListFragment;
import nz.co.codebros.quakesnz.list.QuakeListModule;
import nz.co.codebros.quakesnz.module.FeatureCollectionModule;
import nz.co.codebros.quakesnz.module.FeatureModule;

/**
 * Created by leandro on 3/07/17.
 */
@Module
public abstract class FeatureListActivityModule {

    @ContributesAndroidInjector(modules = {
            QuakeListModule.class,
            FeatureCollectionModule.class,
            FeatureModule.class
    })
    abstract QuakeListFragment quakeListFragment();

    @ContributesAndroidInjector(modules = FeatureModule.class)
    abstract QuakeMapFragment quakeMapFragment();
}
