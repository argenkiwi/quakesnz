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
import nz.co.codebros.quakesnz.repository.Publisher;

/**
 * Created by leandro on 17/06/17.
 */
@Module
public class FeatureCollectionModule {

    @Provides
    static Publisher<FeatureCollection> featureCollectionPublisher(
            Subject<FeatureCollection> subject
    ){
        return new Publisher<>(subject);
    }

    @Provides
    static FeatureCollectionRepository featureCollectionRepository(
            Publisher<FeatureCollection> publisher,
            GeonetService service
    ) {
        return new FeatureCollectionRepository(publisher, service);
    }

    @Provides
    static LoadFeaturesInteractor interactor(
            SharedPreferences preferences,
            FeatureCollectionRepository repository
    ) {
        return new LoadFeaturesInteractorImpl(preferences, repository);
    }
}
