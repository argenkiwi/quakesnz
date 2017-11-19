package nz.co.codebros.quakesnz

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import nz.co.codebros.quakesnz.settings.SettingsActivity
import nz.co.codebros.quakesnz.settings.SettingsActivityModule
import nz.co.codebros.quakesnz.ui.FeatureDetailActivity
import nz.co.codebros.quakesnz.ui.FeatureListActivity
import nz.co.codebros.quakesnz.ui.FeatureListActivityModule
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Leandro on 2/11/2017.
 */
@Module
internal abstract class ApplicationModule {

    @ContributesAndroidInjector(modules = arrayOf(FeatureListActivityModule::class))
    internal abstract fun featureListActivity(): FeatureListActivity

    @ContributesAndroidInjector(modules = arrayOf(FeatureDetailActivity.Module::class))
    internal abstract fun featureDetailActivity(): FeatureDetailActivity

    @ContributesAndroidInjector(modules = arrayOf(SettingsActivityModule::class))
    internal abstract fun settingsActivity(): SettingsActivity

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
        @Named("app")
        fun tracker(application: QuakesNZApplication): Tracker =
                GoogleAnalytics.getInstance(application).newTracker(R.xml.app_tracker)
    }
}
