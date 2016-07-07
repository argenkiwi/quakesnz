package nz.co.codebros.quakesnz.component;

import dagger.Component;
import nz.co.codebros.quakesnz.module.QuakeDetailModule;
import nz.co.codebros.quakesnz.scope.ViewScope;
import nz.co.codebros.quakesnz.ui.QuakeDetailFragment;

/**
 * Created by leandro on 7/07/16.
 */
@ViewScope
@Component(dependencies = ApplicationComponent.class, modules = QuakeDetailModule.class)
public interface QuakeDetailComponent {
    void inject(QuakeDetailFragment fragment);
}
