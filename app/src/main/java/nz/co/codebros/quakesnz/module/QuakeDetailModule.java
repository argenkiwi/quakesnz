package nz.co.codebros.quakesnz.module;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.GetFeatureInteractor;
import nz.co.codebros.quakesnz.presenter.QuakeDetailPresenter;
import nz.co.codebros.quakesnz.view.QuakeDetailView;

/**
 * Created by leandro on 7/07/16.
 */
@Module
public class QuakeDetailModule {
    private QuakeDetailView view;

    public QuakeDetailModule(QuakeDetailView view) {
        this.view = view;
    }

    @Provides
    public GetFeatureInteractor provideInteractor(GeonetService service) {
        return new GetFeatureInteractor(service);
    }

    @Provides
    public QuakeDetailPresenter providePresenter(GetFeatureInteractor interactor) {
        return new QuakeDetailPresenter(view, interactor);
    }
}
