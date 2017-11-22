package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

/**
 * Created by Leandro on 2/11/2017.
 */
@Module
object QuakeDetailModule {

    @JvmStatic
    @Provides
    internal fun viewState() = MutableLiveData<QuakeDetailViewState>()

    @JvmStatic
    @Provides
    internal fun quakeDetailViewModel(
            fragment: QuakeDetailFragment,
            factory: QuakeDetailViewModel.Factory
    ) = ViewModelProviders.of(fragment, factory).get(QuakeDetailViewModel::class.java)
}
