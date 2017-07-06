package nz.co.codebros.quakesnz.ui;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment;
import nz.co.codebros.quakesnz.detail.QuakeDetailModule;
import nz.co.codebros.quakesnz.map.QuakeMapFragment;
import nz.co.codebros.quakesnz.module.FeatureModule;

/**
 * Created by leandro on 3/07/17.
 */
@Module
public abstract class FeatureDetailActivityModule {

    @ContributesAndroidInjector(modules = {
            QuakeDetailModule.class,
            FeatureModule.class
    })
    abstract QuakeDetailFragment quakeDetailFragment();

    @ContributesAndroidInjector(modules = FeatureModule.class)
    abstract QuakeMapFragment quakeMapFragment();
}
