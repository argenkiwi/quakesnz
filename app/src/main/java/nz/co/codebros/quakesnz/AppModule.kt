package nz.co.codebros.quakesnz

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.codebros.quakesnz.list.model.QuakeListState
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("cacheDir")
    fun cacheDir(@ApplicationContext context: Context): File = context.cacheDir

    @Provides
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun tracker(@ApplicationContext context: Context) = FirebaseAnalytics.getInstance(context)

    @Provides
    @Singleton
    fun quakeListEvents(): PublishProcessor<QuakeListEvent> = PublishProcessor.create()

    @Provides
    @Singleton
    fun quakeListState(): BehaviorProcessor<QuakeListState> = BehaviorProcessor.create()
}
