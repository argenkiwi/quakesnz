package nz.co.codebros.quakesnz.component;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.module.ApplicationModule;
import nz.co.codebros.quakesnz.module.InjectorsModule;
import nz.co.codebros.quakesnz.module.ServicesModule;
import nz.co.codebros.quakesnz.module.SubjectsModule;

/**
 * Created by leandro on 9/07/15.
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        InjectorsModule.class,
        ApplicationModule.class,
        ServicesModule.class,
        SubjectsModule.class
})
interface ApplicationComponent extends AndroidInjector<QuakesNZApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<QuakesNZApplication> {
    }
}
