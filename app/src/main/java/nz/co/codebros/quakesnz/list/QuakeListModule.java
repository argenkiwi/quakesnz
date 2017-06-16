package nz.co.codebros.quakesnz.list;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.BehaviorSubject;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractorImpl;
import nz.co.codebros.quakesnz.model.FeatureCollection;

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
            BehaviorSubject<FeatureCollection> featureCollectionBehaviourSubject
    ) {
        return new GetFeaturesInteractorImpl(service, preferences, featureCollectionBehaviourSubject);
    }

    @Provides
    QuakeListPresenter providePresenter(
            GetFeaturesInteractor interactor,
            BehaviorSubject<FeatureCollection> featureCollectionBehaviorSubject
    ) {
        return new QuakeListPresenter(view, interactor, featureCollectionBehaviorSubject);
    }
}
