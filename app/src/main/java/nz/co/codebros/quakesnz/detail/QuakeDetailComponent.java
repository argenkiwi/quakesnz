package nz.co.codebros.quakesnz.detail;

import dagger.Subcomponent;
import nz.co.codebros.quakesnz.module.FeatureModule;
import nz.co.codebros.quakesnz.scope.ViewScope;

/**
 * Created by leandro on 7/07/16.
 */
@ViewScope
@Subcomponent(modules = {
        QuakeDetailModule.class,
        FeatureModule.class
})
public interface QuakeDetailComponent {
    void inject(QuakeDetailFragment fragment);
}
