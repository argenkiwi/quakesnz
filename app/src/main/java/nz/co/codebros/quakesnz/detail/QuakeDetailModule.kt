package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by Leandro on 2/11/2017.
 */
@Module
abstract class QuakeDetailModule {

    @Binds
    internal abstract fun quakeDetailView(fragment: QuakeDetailFragment): QuakeDetailView

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun featureLiveData() = MutableLiveData<Feature>()

        @JvmStatic
        @Provides
        internal fun quakeDetailViewModel(
                fragment: QuakeDetailFragment,
                factory: QuakeDetailViewModel.Factory
        ) = ViewModelProviders.of(fragment, factory).get(QuakeDetailViewModel::class.java)
    }
}
