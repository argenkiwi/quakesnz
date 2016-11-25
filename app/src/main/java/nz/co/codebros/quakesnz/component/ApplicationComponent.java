package nz.co.codebros.quakesnz.component;

import android.content.SharedPreferences;

import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.module.ApplicationModule;
import nz.co.codebros.quakesnz.module.ServicesModule;

/**
 * Created by leandro on 9/07/15.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        ServicesModule.class
})
public interface ApplicationComponent {

    Gson getGson();

    GeonetService getService();

    SharedPreferences getSharedPreferences();

    @Named("app")
    Tracker getTracker();
}
