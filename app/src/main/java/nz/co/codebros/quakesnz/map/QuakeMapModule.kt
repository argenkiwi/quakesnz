package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

/**
 * Created by Leandro on 16/02/2018.
 */
@Module
object QuakeMapModule {

    @JvmStatic
    @Provides
    fun viewModel(
            fragment: QuakeMapFragment,
            factory: QuakeMapViewModel.Factory
    ) = ViewModelProviders.of(fragment, factory).get(QuakeMapViewModel::class.java)
}