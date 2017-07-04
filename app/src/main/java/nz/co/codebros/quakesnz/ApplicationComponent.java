package nz.co.codebros.quakesnz;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import nz.co.codebros.quakesnz.core.ServicesModule;
import nz.co.codebros.quakesnz.module.SubjectsModule;

/**
 * Created by leandro on 9/07/15.
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        ServicesModule.class,
        SubjectsModule.class
})
interface ApplicationComponent extends AndroidInjector<QuakesNZApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<QuakesNZApplication> {
    }
}
