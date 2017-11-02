package nz.co.codebros.quakesnz.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.io.File

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import nz.co.codebros.quakesnz.core.model.Coordinates
import nz.co.codebros.quakesnz.core.model.CoordinatesTypeAdapter
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by leandro on 22/07/16.
 */
@Module
object ServicesModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun geonetService(retrofit: Retrofit) = retrofit.create(GeonetService::class.java)

    @JvmStatic
    @Provides
    @Singleton
    internal fun gson() = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
            .registerTypeAdapter(Coordinates::class.java, CoordinatesTypeAdapter())
            .create()

    @JvmStatic
    @Provides
    internal fun httpLoggingInterceptor() = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

    @JvmStatic
    @Provides
    internal fun retrofit(client: OkHttpClient, gson: Gson) = Retrofit.Builder()
            .baseUrl("http://api.geonet.org.nz/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @JvmStatic
    @Provides
    internal fun okHttpClient(
            @Named("cacheDir") cacheDir: File,
            interceptor: HttpLoggingInterceptor
    ) = OkHttpClient()
            .newBuilder()
            .cache(Cache(cacheDir, (2 * 1024 * 1024).toLong())) // 2Mb
            .addInterceptor(interceptor)
            .build()
}
