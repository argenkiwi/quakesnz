package nz.co.codebros.quakesnz.module;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractorImpl;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.publisher.Publisher;
import nz.co.codebros.quakesnz.repository.Repository;

/**
 * Created by leandro on 17/06/17.
 */
@Module
public class FeatureCollectionModule {

    @Provides
    @Singleton
    static Subject<FeatureCollection> featureCollectionSubject(){
        return BehaviorSubject.create();
    }

    @Provides
    static Repository<FeatureCollection> featureCollectionRepository(
            Subject<FeatureCollection> subject
    ){
        return new Repository<>(subject);
    }

    @Provides
    static Publisher<FeatureCollection> featureCollectionPublisher(
            Subject<FeatureCollection> subject
    ){
        return new Publisher<>(subject);
    }

    @Provides
    static GetFeaturesInteractor interactor(
            GeonetService service,
            SharedPreferences preferences,
            Publisher<FeatureCollection> publisher
    ) {
        return new GetFeaturesInteractorImpl(service, preferences, publisher);
    }
}
