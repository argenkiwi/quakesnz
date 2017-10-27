package nz.co.codebros.quakesnz.settings;

import android.content.SharedPreferences;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;

/**
 * Created by leandro on 6/07/17.
 */
@Module
abstract class SettingsModule {

    @Binds
    abstract SettingsView settingsView(SettingsFragment fragment);
}
