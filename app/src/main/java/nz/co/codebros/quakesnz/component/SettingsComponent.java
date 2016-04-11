package nz.co.codebros.quakesnz.component;

import dagger.Component;
import nz.co.codebros.quakesnz.module.SettingsModule;
import nz.co.codebros.quakesnz.scope.ViewScope;
import nz.co.codebros.quakesnz.ui.SettingsFragment;

/**
 * Created by leandro on 12/04/16.
 */
@ViewScope
@Component(dependencies = ApplicationComponent.class, modules = SettingsModule.class)
public interface SettingsComponent {
    void inject(SettingsFragment fragment);
}
