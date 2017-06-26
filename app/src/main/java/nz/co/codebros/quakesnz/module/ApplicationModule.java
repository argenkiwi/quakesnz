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
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;

/**
 * Created by leandro on 9/07/15.
 */
@Module
public class ApplicationModule {

    @Provides
    @Named("cacheDir")
    File provideCacheDir(QuakesNZApplication application){
        return application.getCacheDir();
    }

    @Provides
    static SharedPreferences provideSharedPreferences(QuakesNZApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    @Named("app")
    static Tracker provideTracker(QuakesNZApplication application) {
        return GoogleAnalytics.getInstance(application).newTracker(R.xml.app_tracker);
    }
}
