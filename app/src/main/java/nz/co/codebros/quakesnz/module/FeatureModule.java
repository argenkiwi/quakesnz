package nz.co.codebros.quakesnz.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.publisher.Publisher;
import nz.co.codebros.quakesnz.repository.FeatureRepository;

/**
 * Created by leandro on 18/06/17.
 */
@Module
public class FeatureModule {

    @Provides
    static FeatureRepository featureRepository(
            Subject<Feature> subject,
            GeonetService service
    ){
        return new FeatureRepository(subject, service);
    }

    @Provides
    static LoadFeatureInteractorImpl getFeatureInteractor(
            FeatureRepository repository
    ) {
        return new LoadFeatureInteractorImpl(repository);
    }

    @Provides
    static Publisher<Feature> featurePublisher(
            Subject<Feature> subject
    ) {
        return new Publisher<>(subject);
    }
}
