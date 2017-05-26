package nz.co.codebros.quakesnz.module;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;

/**
 * Created by leandro on 9/07/15.
 */
@Module
public class ApplicationModule {

    private final QuakesNZApplication mApplication;

    public ApplicationModule(QuakesNZApplication application) {
        mApplication = application;
    }

    @Provides
    @Named("cacheDir")
    File provideCacheDir(){
        return mApplication.getCacheDir();
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }

    @Provides
    @Singleton
    @Named("app")
    Tracker provideTracker() {
        return GoogleAnalytics.getInstance(mApplication).newTracker(R.xml.app_tracker);
    }
}
