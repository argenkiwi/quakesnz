package nz.co.codebros.quakesnz.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment;
import nz.co.codebros.quakesnz.detail.QuakeDetailModule;
import nz.co.codebros.quakesnz.list.QuakeListFragment;
import nz.co.codebros.quakesnz.list.QuakeListModule;

/**
 * Created by leandro on 26/06/17.
 */
@Module
public abstract class InjectorsModule {
    @ContributesAndroidInjector(modules = {QuakeDetailModule.class, FeatureModule.class})
    abstract QuakeDetailFragment quakeDetailFragment();

    @ContributesAndroidInjector(modules = {QuakeListModule.class, FeatureCollectionModule.class})
    abstract QuakeListFragment quakeListFragment();
}
