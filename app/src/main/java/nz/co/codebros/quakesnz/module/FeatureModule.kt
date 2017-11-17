package nz.co.codebros.quakesnz.module

import dagger.Module
import dagger.Provides
import nz.co.codebros.quakesnz.core.GeonetService
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl
import nz.co.codebros.quakesnz.repository.FeatureRepository

/**
 * Created by leandro on 18/06/17.
 */
@Module
object FeatureModule {

    @JvmStatic
    @Provides
    internal fun getFeatureInteractor(
            service: GeonetService,
            repository: FeatureRepository
    ): LoadFeatureInteractor = LoadFeatureInteractorImpl(service, repository)
}
