package nz.co.codebros.quakesnz.module;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.presenter.SettingsPresenter;
import nz.co.codebros.quakesnz.ui.SettingsFragment;
import nz.co.codebros.quakesnz.view.SettingsView;

/**
 * Created by leandro on 12/04/16.
 */
@Module
public class SettingsModule {
    private final SettingsView view;

    public SettingsModule(SettingsView view) {
        this.view = view;
    }

    @Provides
    public SettingsPresenter providePresenter(SharedPreferences preferences){
        return new SettingsPresenter(preferences);
    }
}
