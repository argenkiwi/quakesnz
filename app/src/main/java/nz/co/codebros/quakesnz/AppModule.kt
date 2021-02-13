package nz.co.codebros.quakesnz

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import nz.co.codebros.quakesnz.scope.ActivityScope
import nz.co.codebros.quakesnz.ui.FeatureDetailActivity
import nz.co.codebros.quakesnz.ui.FeatureListActivity
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
internal abstract class AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FeatureListActivity.Module::class])
    internal abstract fun featureListActivity(): FeatureListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [FeatureDetailActivity.Module::class])
    internal abstract fun featureDetailActivity(): FeatureDetailActivity

    companion object {

        @Provides
        @Named("cacheDir")
        fun cacheDir(application: QuakesNZ): File = application.cacheDir

        @Provides
        fun sharedPreferences(application: QuakesNZ): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(application)

        @Provides
        @Singleton
        fun tracker(application: QuakesNZ) = FirebaseAnalytics.getInstance(application)
    }
}
