package nz.co.codebros.quakesnz.list;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractorImpl;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.repository.Repository;

/**
 * Created by leandro on 9/07/15.
 */
@Module
public class QuakeListModule {
    private QuakeListView view;

    QuakeListModule(QuakeListView view) {
        this.view = view;
    }

    @Provides
    GetFeaturesInteractor provideInteractor(
            GeonetService service,
            SharedPreferences preferences,
            Subject<FeatureCollection> featureCollectionSubject
    ) {
        return new GetFeaturesInteractorImpl(service, preferences, featureCollectionSubject);
    }

    @Provides
    QuakeListPresenter providePresenter(
            GetFeaturesInteractor interactor,
            Subject<FeatureCollection> featureCollectionSubject
    ) {
        return new QuakeListPresenter(view, interactor, new Repository<>(featureCollectionSubject));
    }
}
