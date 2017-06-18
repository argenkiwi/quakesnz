package nz.co.codebros.quakesnz.detail;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.repository.Publisher;

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
    QuakeDetailPresenter providePresenter(
            Publisher<Feature> publisher,
            LoadFeatureInteractorImpl interactor
    ) {
        return new QuakeDetailPresenter(view, publisher, interactor);
    }
}
