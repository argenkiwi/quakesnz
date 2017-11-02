package nz.co.codebros.quakesnz.module

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.core.model.FeatureCollection

/**
 * Created by leandro on 18/06/17.
 */
@Module
object SubjectsModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun featureCollectionSubject(): Subject<FeatureCollection> = BehaviorSubject.create()

    @JvmStatic
    @Provides
    @Singleton
    internal fun featureSubject(): Subject<Feature> = BehaviorSubject.create()
}
