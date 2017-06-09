package nz.co.codebros.quakesnz.detail;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.GetFeatureInteractor;
import nz.co.codebros.quakesnz.interactor.GetFeatureInteractorImpl;

/**
 * Created by leandro on 7/07/16.
 */
@Module
public class QuakeDetailModule {
    private QuakeDetailView view;

    QuakeDetailModule(QuakeDetailView view) {
        this.view = view;
    }

    @Provides
    GetFeatureInteractor provideInteractor(GeonetService service) {
        return new GetFeatureInteractorImpl(service);
    }

    @Provides
    QuakeDetailPresenter providePresenter(GetFeatureInteractor interactor) {
        return new QuakeDetailPresenter(view, interactor);
    }
}
