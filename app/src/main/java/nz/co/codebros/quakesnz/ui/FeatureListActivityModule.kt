package nz.co.codebros.quakesnz.ui

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.BackpressureStrategy
import nz.co.codebros.quakesnz.detail.QuakeDetailModel
import nz.co.codebros.quakesnz.list.QuakeListFragment
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.map.QuakeMapState
import nz.co.codebros.quakesnz.scope.FragmentScope
import nz.co.codebros.quakesnz.util.toLiveData

/**
 * Created by leandro on 3/07/17.
 */
@Module
abstract class FeatureListActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun quakeListFragment(): QuakeListFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun quakeMapFragment(): QuakeMapFragment

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun quakeMapState(
                quakeDetailModel: QuakeDetailModel
        ) = quakeDetailModel.stateObservable
                .map { QuakeMapState(it.feature?.geometry?.coordinates) }
                .toLiveData(BackpressureStrategy.LATEST)
    }
}
