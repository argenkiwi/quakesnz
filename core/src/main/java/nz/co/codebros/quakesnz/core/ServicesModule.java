package nz.co.codebros.quakesnz.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.core.model.Coordinates;
import nz.co.codebros.quakesnz.core.model.CoordinatesTypeAdapter;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leandro on 22/07/16.
 */
@Module
public abstract class ServicesModule {

    @Provides
    @Singleton
    static GeonetService provideGeonetService(Retrofit retrofit) {
        return retrofit.create(GeonetService.class);
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                .registerTypeAdapter(Coordinates.class, new CoordinatesTypeAdapter())
                .create();
    }

    @Provides
    static HttpLoggingInterceptor provideInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    static Retrofit provideRestAdapter(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("http://api.geonet.org.nz/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    static OkHttpClient provideOkHttpClient(
            @Named("cacheDir") File cacheDir,
            HttpLoggingInterceptor interceptor
    ) {
        return new OkHttpClient().newBuilder()
                .cache(new Cache(cacheDir, 2 * 1024 * 1024)) // 2Mb
                .addInterceptor(interceptor)
                .build();
    }
}
