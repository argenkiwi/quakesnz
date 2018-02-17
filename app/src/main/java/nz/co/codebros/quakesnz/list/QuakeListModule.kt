package nz.co.codebros.quakesnz.list

import dagger.Binds
import dagger.Module
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractorImpl

/**
 * Created by Leandro on 27/11/2017.
 */
@Module
abstract class QuakeListModule {

    @Binds
    abstract fun loadFeaturesInteractor(
            loadFeaturesInteractorImpl: LoadFeaturesInteractorImpl
    ): LoadFeaturesInteractor
}