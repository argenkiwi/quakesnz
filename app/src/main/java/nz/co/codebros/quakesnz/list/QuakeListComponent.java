package nz.co.codebros.quakesnz.list;

import dagger.Subcomponent;
import nz.co.codebros.quakesnz.module.FeatureCollectionModule;
import nz.co.codebros.quakesnz.scope.ViewScope;

/**
 * Created by leandro on 9/07/15.
 */
@ViewScope
@Subcomponent(modules = {
        QuakeListModule.class,
        FeatureCollectionModule.class
})
public interface QuakeListComponent {
    void inject(QuakeListFragment quakeListFragment);
}
