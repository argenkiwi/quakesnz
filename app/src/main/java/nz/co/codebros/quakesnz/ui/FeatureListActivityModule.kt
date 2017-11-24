package nz.co.codebros.quakesnz.ui

import android.arch.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.core.data.FeatureCollection
import nz.co.codebros.quakesnz.list.QuakeList
import nz.co.codebros.quakesnz.list.QuakeListFragment
import nz.co.codebros.quakesnz.map.QuakeMap
import nz.co.codebros.quakesnz.map.QuakeMapFragment
import nz.co.codebros.quakesnz.scope.ActivityScope
import nz.co.codebros.quakesnz.scope.FragmentScope

/**
 * Created by leandro on 3/07/17.
 */
@Module
abstract class FeatureListActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = arrayOf(QuakeList.Module::class))
    internal abstract fun quakeListFragment(): QuakeListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = arrayOf(QuakeMap.Module::class))
    internal abstract fun quakeMapFragment(): QuakeMapFragment

    @Binds
    internal abstract fun featureObservable(subject: Subject<Feature>): Observable<Feature>

    @Module
    companion object {
        @JvmStatic
        @Provides
        @ActivityScope
        internal fun featureCollectionSubject(): Subject<FeatureCollection> = PublishSubject.create()

        @JvmStatic
        @Provides
        @ActivityScope
        internal fun featureSubject(): Subject<Feature> = BehaviorSubject.create()

        @JvmStatic
        @Provides
        fun viewModel(
                activity: FeatureListActivity,
                factory: FeatureListActivityViewModel.Factory
        ) = ViewModelProviders.of(activity, factory).get(FeatureListActivityViewModel::class.java)
    }
}
