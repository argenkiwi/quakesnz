package nz.co.codebros.quakesnz.detail;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.repository.FeatureRepository;

/**
 * Created by leandro on 7/07/16.
 */
@Module
public abstract class QuakeDetailModule {
    abstract QuakeDetailView quakeDetailView(QuakeDetailFragment fragment);

    @Provides
    QuakeDetailPresenter providePresenter(
            QuakeDetailView view,
            FeatureRepository repository,
            LoadFeatureInteractorImpl interactor
    ) {
        return new QuakeDetailPresenter(view, repository, interactor);
    }
}
