package nz.co.codebros.quakesnz.ui

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Observable
import nz.co.codebros.quakesnz.list.QuakeListFragment
import nz.co.codebros.quakesnz.list.QuakeListModel
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.map.QuakeMapState
import nz.co.codebros.quakesnz.scope.FragmentScope

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
        internal fun quakeMapStateObservable(
                quakeListModel: QuakeListModel
        ): Observable<QuakeMapState> = quakeListModel.stateObservable
                .map { QuakeMapState(it.selectedFeature?.geometry?.coordinates) }
    }
}
