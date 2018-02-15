package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

/**
 * Created by Leandro on 16/02/2018.
 */
@Module
object QuakeDetailModule {

    @JvmStatic
    @Provides
    internal fun viewModel(
            fragment: QuakeDetailFragment,
            factory: QuakeDetailViewModel.Factory
    ) = ViewModelProviders.of(fragment, factory).get(QuakeDetailViewModel::class.java)
}