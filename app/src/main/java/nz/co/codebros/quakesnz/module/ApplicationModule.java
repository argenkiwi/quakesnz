package nz.co.codebros.quakesnz.module;

import android.content.Context;

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
import de.greenrobot.event.EventBus;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.RequestHandler;
import nz.co.codebros.quakesnz.utils.DateDeserializer;
import nz.co.codebros.quakesnz.utils.LatLngAdapter;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

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
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    EventBus provideEventBus(){
        return EventBus.getDefault();
    }

    @Provides
    Gson providesGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LatLng.class, new LatLngAdapter())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();
    }

    @Provides
    @Singleton
    GeonetService provideGeonetService(RestAdapter restAdapter) {
        return restAdapter.create(GeonetService.class);
    }

    @Provides
    RestAdapter provideRestAdapter(Gson gson) {
        return new RestAdapter.Builder()
                .setEndpoint("http://www.geonet.org.nz")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    @Provides
    @Singleton
    RequestHandler provideRequestHandler(EventBus bus, GeonetService service){
        RequestHandler requestHandler = new RequestHandler(bus,service);
        bus.register(requestHandler);
        return requestHandler;
    }

    @Provides
    @Named("app")
    Tracker provideTracker() {
        return GoogleAnalytics.getInstance(mApplication).newTracker(R.xml.app_tracker);
    }
}
