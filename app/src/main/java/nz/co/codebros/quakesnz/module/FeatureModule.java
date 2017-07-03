package nz.co.codebros.quakesnz.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.core.GeonetService;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.repository.FeatureRepository;

/**
 * Created by leandro on 18/06/17.
 */
@Module
public class FeatureModule {

    @Provides
    static FeatureRepository featureRepository(Subject<Feature> subject){
        return new FeatureRepository(subject);
    }

    @Provides
    static LoadFeatureInteractorImpl getFeatureInteractor(
            GeonetService service,
            FeatureRepository repository
    ) {
        return new LoadFeatureInteractorImpl(service, repository);
    }
}
