package nz.co.codebros.quakesnz.component;

import android.content.Context;

import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import de.greenrobot.event.EventBus;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.module.ApplicationModule;

/**
 * Created by leandro on 9/07/15.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Context getApplicationContext();

    EventBus getEventBus();

    GeonetService getGeonetService();

    Gson getGson();

    @Named("app")
    Tracker getTracker();

    void inject(QuakesNZApplication application);
}
