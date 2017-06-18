package nz.co.codebros.quakesnz.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.repository.Publisher;

/**
 * Created by leandro on 18/06/17.
 */
@Module
public class FeatureModule {

    @Provides
    static Publisher<Feature> featurePublisher(
            Subject<Feature> subject
    ) {
        return new Publisher<>(subject);
    }

    @Provides
    static LoadFeatureInteractorImpl getFeatureInteractor(
            Subject<FeatureCollection> subject,
            GeonetService service,
            Publisher<Feature> publisher
    ) {
        return new LoadFeatureInteractorImpl(subject, service, publisher);
    }
}
