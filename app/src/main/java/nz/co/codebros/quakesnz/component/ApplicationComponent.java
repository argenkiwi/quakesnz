package nz.co.codebros.quakesnz.component;

import android.content.Context;

import com.google.android.gms.analytics.Tracker;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.module.ApplicationModule;

/**
 * Created by leandro on 9/07/15.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Context getApplicationContext();

    GeonetService getGeonetService();

    @Named("app")
    Tracker getTracker();
}
