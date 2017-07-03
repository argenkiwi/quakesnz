package nz.co.codebros.quakesnz.module;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.core.GeonetService;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractorImpl;
import nz.co.codebros.quakesnz.core.model.FeatureCollection;
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository;

/**
 * Created by leandro on 17/06/17.
 */
@Module
public abstract class FeatureCollectionModule {

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
            FeatureCollectionRepository repository

    ) {
        return new LoadFeaturesInteractorImpl(preferences, service, repository);
    }
}
