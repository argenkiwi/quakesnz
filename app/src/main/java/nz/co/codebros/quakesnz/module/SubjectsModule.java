package nz.co.codebros.quakesnz.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;

/**
 * Created by leandro on 18/06/17.
 */
@Module
public class SubjectsModule {
    @Provides
    @Singleton
    static Subject<FeatureCollection> featureCollectionSubject() {
        return ReplaySubject.create();
    }

    @Provides
    @Singleton
    static Subject<Feature> featureSubject() {
        return ReplaySubject.create();
    }
}
