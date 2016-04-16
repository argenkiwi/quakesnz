package nz.co.codebros.quakesnz.module;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.utils.LatLngTypeAdapter;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
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
    @Singleton
    GeonetService provideGeonetService(Retrofit retrofit) {
        return retrofit.create(GeonetService.class);
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                .registerTypeAdapter(LatLng.class, new LatLngTypeAdapter())
                .create();
    }

    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .cache(new Cache(mApplication.getCacheDir(), 2 * 1024 * 1024)) // 2Mb
                .build();
    }

    @Provides
    Retrofit provideRestAdapter(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("http://api.geonet.org.nz/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
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
