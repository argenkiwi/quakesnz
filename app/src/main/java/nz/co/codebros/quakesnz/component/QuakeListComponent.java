package nz.co.codebros.quakesnz.component;

import dagger.Component;
import nz.co.codebros.quakesnz.module.QuakeListModule;
import nz.co.codebros.quakesnz.scope.FragmentScope;
import nz.co.codebros.quakesnz.ui.QuakeListFragment;

/**
 * Created by leandro on 9/07/15.
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = QuakeListModule.class)
public interface QuakeListComponent {
    void inject(QuakeListFragment quakeListFragment);
}
