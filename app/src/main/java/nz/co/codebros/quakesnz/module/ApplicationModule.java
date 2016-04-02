package nz.co.codebros.quakesnz.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.utils.DateDeserializer;
import nz.co.codebros.quakesnz.utils.LatLngTypeAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


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
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    GeonetService provideGeonetService(Retrofit retrofit) {
        return retrofit.create(GeonetService.class);
    }

    @Provides
    Retrofit provideRestAdapter(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("http://www.geonet.org.nz")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Named("app")
    Tracker provideTracker() {
        return GoogleAnalytics.getInstance(mApplication).newTracker(R.xml.app_tracker);
    }

    @Provides
    Gson providesGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LatLng.class, new LatLngTypeAdapter())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();
    }
}
