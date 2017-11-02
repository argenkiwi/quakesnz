package nz.co.codebros.quakesnz;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import nz.co.codebros.quakesnz.settings.SettingsActivity;
import nz.co.codebros.quakesnz.settings.SettingsActivityModule;
import nz.co.codebros.quakesnz.ui.FeatureDetailActivity;
import nz.co.codebros.quakesnz.ui.FeatureDetailActivityModule;
import nz.co.codebros.quakesnz.ui.FeatureListActivity;
import nz.co.codebros.quakesnz.ui.FeatureListActivityModule;

/**
 * Created by Leandro on 2/11/2017.
 */
@Module
abstract class ApplicationModule {

    @ContributesAndroidInjector(modules = FeatureListActivityModule.class)
    abstract FeatureListActivity featureListActivity();

    @ContributesAndroidInjector(modules = FeatureDetailActivityModule.class)
    abstract FeatureDetailActivity featureDetailActivity();

    @ContributesAndroidInjector(modules = SettingsActivityModule.class)
    abstract SettingsActivity settingsActivity();

    @Provides
    @Named("cacheDir")
    static File cacheDir(QuakesNZApplication application) {
        return application.getCacheDir();
    }

    @Provides
    static SharedPreferences sharedPreferences(QuakesNZApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    @Named("app")
    static Tracker tracker(QuakesNZApplication application) {
        return GoogleAnalytics.getInstance(application).newTracker(R.xml.app_tracker);
    }
}
