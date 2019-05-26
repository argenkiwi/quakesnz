package nz.co.codebros.quakesnz.core

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import nz.co.codebros.quakesnz.core.moshi.CoordinatesTypeAdapter
import nz.co.codebros.quakesnz.core.moshi.DateTypeAdapter
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
object ServicesModule {

    @JvmStatic
    @Provides
    internal fun httpLoggingInterceptor() = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

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

    @JvmStatic
    @Provides
    internal fun moshi() = Moshi.Builder()
            .add(DateTypeAdapter("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
            .add(CoordinatesTypeAdapter())
            .build()

    @JvmStatic
    @Provides
    internal fun retrofit(client: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
            .baseUrl("http://api.geonet.org.nz/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @JvmStatic
    @Provides
    @Singleton
    internal fun geonetService(retrofit: Retrofit) = retrofit.create(GeonetService::class.java)
}
