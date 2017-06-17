package nz.co.codebros.quakesnz.list;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository;
import nz.co.codebros.quakesnz.repository.Publisher;

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
            LoadFeaturesInteractor interactor,
            Publisher<FeatureCollection> publisher
    ) {
        return new QuakeListPresenter(view, interactor, publisher);
    }
}
