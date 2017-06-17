package nz.co.codebros.quakesnz.component;

import javax.inject.Singleton;

import dagger.Component;
import nz.co.codebros.quakesnz.detail.QuakeDetailComponent;
import nz.co.codebros.quakesnz.detail.QuakeDetailModule;
import nz.co.codebros.quakesnz.list.QuakeListComponent;
import nz.co.codebros.quakesnz.list.QuakeListModule;
import nz.co.codebros.quakesnz.module.ApplicationModule;
import nz.co.codebros.quakesnz.module.FeatureCollectionModule;
import nz.co.codebros.quakesnz.module.ServicesModule;

/**
 * Created by leandro on 9/07/15.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        ServicesModule.class,
        FeatureCollectionModule.class
})
public interface ApplicationComponent {
    QuakeListComponent plus(QuakeListModule module);

    QuakeDetailComponent plus(QuakeDetailModule module);
}
