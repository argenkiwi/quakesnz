package nz.co.codebros.quakesnz.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nz.co.codebros.quakesnz.detail.QuakeDetailFragment;
import nz.co.codebros.quakesnz.detail.QuakeDetailModule;

/**
 * Created by leandro on 26/06/17.
 */
@Module
public abstract class InjectorsModule {
    @ContributesAndroidInjector(modules = {
            QuakeDetailModule.class,
            FeatureModule.class
    })
    abstract QuakeDetailFragment quakeDetailFragment();
}
