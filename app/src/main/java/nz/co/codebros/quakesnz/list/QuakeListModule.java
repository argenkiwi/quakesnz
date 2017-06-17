package nz.co.codebros.quakesnz.list;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
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
    QuakeListPresenter presenter(
            GetFeaturesInteractor interactor,
            Repository<FeatureCollection> repository
    ) {
        return new QuakeListPresenter(view, interactor, repository);
    }
}
