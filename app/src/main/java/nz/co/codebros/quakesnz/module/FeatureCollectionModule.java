package nz.co.codebros.quakesnz.module;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractorImpl;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository;
import nz.co.codebros.quakesnz.publisher.Publisher;

/**
 * Created by leandro on 17/06/17.
 */
@Module
public class FeatureCollectionModule {

    @Provides
    static FeatureCollectionRepository featureCollectionRepository(
            Subject<FeatureCollection> subject
    ) {
        return new FeatureCollectionRepository(subject);
    }

    @Provides
    static LoadFeaturesInteractor interactor(
            SharedPreferences preferences,
            GeonetService service,
            Subject<FeatureCollection> subject

    ) {
        return new LoadFeaturesInteractorImpl(preferences, service, subject);
    }

    @Provides
    static Publisher<FeatureCollection> featureCollectionPublisher(
            Subject<FeatureCollection> subject
    ){
        return new Publisher<>(subject);
    }
}
