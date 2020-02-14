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

    private val httpLoggingInterceptor
        get() = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val moshi
        get() = Moshi.Builder()
                .add(DateTypeAdapter("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                .add(CoordinatesTypeAdapter())
                .build()

    private fun okHttpClient(cacheDir: File) = OkHttpClient()
            .newBuilder()
            .cache(Cache(cacheDir, (2 * 1024 * 1024).toLong())) // 2Mb
            .addInterceptor(httpLoggingInterceptor)
            .build()

    private fun retrofit(client: OkHttpClient) = Retrofit.Builder()
            .baseUrl("https://api.geonet.org.nz/")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @JvmStatic
    @Provides
    @Singleton
    internal fun geonetService(
            @Named("cacheDir") cacheDir: File
    ) = retrofit(okHttpClient(cacheDir)).create(GeonetService::class.java)
}
