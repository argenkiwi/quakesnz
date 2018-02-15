package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
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

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun viewModel(
                fragment: QuakeListFragment,
                factory: QuakeListViewModel.Factory
        ) = ViewModelProviders.of(fragment, factory).get(QuakeListViewModel::class.java)
    }
}