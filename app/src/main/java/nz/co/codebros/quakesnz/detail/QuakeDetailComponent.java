package nz.co.codebros.quakesnz.detail;

import dagger.Subcomponent;
import nz.co.codebros.quakesnz.scope.ViewScope;

/**
 * Created by leandro on 7/07/16.
 */
@ViewScope
@Subcomponent(modules = QuakeDetailModule.class)
public interface QuakeDetailComponent {
    void inject(QuakeDetailFragment fragment);
}
