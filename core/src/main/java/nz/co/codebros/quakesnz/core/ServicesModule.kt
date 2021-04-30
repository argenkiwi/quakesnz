package nz.co.codebros.quakesnz.core

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.core.data.FeatureCollection
import nz.co.codebros.quakesnz.core.moshi.CoordinatesTypeAdapter
import nz.co.codebros.quakesnz.core.moshi.DateTypeAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    private val httpLoggingInterceptor
        get() = HttpLoggingInterceptor().setLevel(when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.BASIC
        })

    private val okHttpClient
        get() = OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

    private val moshi
        get() = Moshi.Builder()
                .add(DateTypeAdapter("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                .add(CoordinatesTypeAdapter())
                .build()

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.geonet.org.nz/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    internal fun geonetService() = retrofit.create(GeonetService::class.java)

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Provides
    @Singleton
    internal fun featuresStore(
            service: GeonetService,
            @Named("cacheDir") cacheDir: File
    ) = StoreBuilder.from(
            Fetcher.of { mmi: Int -> service.getQuakes(mmi) },
            SourceOfTruth.Companion.of(
                    nonFlowReader = { mmi: Int ->
                        val file = File(cacheDir, "MMI$mmi.obj")
                        when {
                            file.exists() -> ObjectInputStream(file.inputStream())
                                    .use { it.readObject() as FeatureCollection }
                            else -> null
                        }
                    },
                    writer = { mmi: Int, featureCollection: FeatureCollection ->
                        val file = File(cacheDir, "MMI$mmi.obj")
                        ObjectOutputStream(file.outputStream()).use { it.writeObject(featureCollection) }
                    }
            )
    ).build()

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Provides
    @Singleton
    internal fun featureStore(
            service: GeonetService,
            @Named("cacheDir") cacheDir: File
    ) = StoreBuilder.from(
            Fetcher.of { publicID: String -> service.getQuake(publicID).features.first() },
            SourceOfTruth.Companion.of(
                    nonFlowReader = { publicId: String ->
                        val file = File(cacheDir, "$publicId.obj")
                        when {
                            file.exists() -> ObjectInputStream(file.inputStream())
                                    .use { it.readObject() as Feature }
                            else -> null
                        }
                    },
                    writer = { publicId: String, feature: Feature ->
                        val file = File(cacheDir, "$publicId.obj")
                        ObjectOutputStream(file.outputStream()).use { it.writeObject(feature) }
                    }
            )
    ).build()
}
