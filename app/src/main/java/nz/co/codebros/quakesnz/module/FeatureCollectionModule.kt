package nz.co.codebros.quakesnz.module

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractorImpl

/**
 * Created by leandro on 17/06/17.
 */
@Module
object FeatureCollectionModule {

    @JvmStatic
    @Provides
    internal fun interactor(
            preferences: SharedPreferences,
            service: GeonetService

    ): LoadFeaturesInteractor = LoadFeaturesInteractorImpl(preferences, service)
}
