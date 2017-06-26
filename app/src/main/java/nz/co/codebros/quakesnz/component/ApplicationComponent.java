package nz.co.codebros.quakesnz.component;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import nz.co.codebros.quakesnz.list.QuakeListComponent;
import nz.co.codebros.quakesnz.list.QuakeListModule;
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
public interface ApplicationComponent {
    QuakeListComponent plus(QuakeListModule module);
}
