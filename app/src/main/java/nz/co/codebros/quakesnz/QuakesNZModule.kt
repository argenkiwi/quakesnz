package nz.co.codebros.quakesnz

import android.content.SharedPreferences
import android.preference.PreferenceManager
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
internal abstract class QuakesNZModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FeatureListActivity.Module::class])
    internal abstract fun featureListActivity(): FeatureListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [FeatureDetailActivity.Module::class])
    internal abstract fun featureDetailActivity(): FeatureDetailActivity

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Named("cacheDir")
        fun cacheDir(application: QuakesNZApplication): File = application.cacheDir

        @JvmStatic
        @Provides
        fun sharedPreferences(application: QuakesNZApplication): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(application)

        @JvmStatic
        @Provides
        @Singleton
        fun tracker(application: QuakesNZApplication) = FirebaseAnalytics.getInstance(application)
    }
}