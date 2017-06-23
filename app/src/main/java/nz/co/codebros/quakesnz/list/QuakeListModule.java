package nz.co.codebros.quakesnz.list;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observer;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractorImpl;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.publisher.Publisher;

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
    SelectFeatureInteractor selectFeatureInteractor(Subject<Feature> subject){
        return new SelectFeatureInteractorImpl(subject);
    }

    @Provides
    QuakeListPresenter presenter(
            LoadFeaturesInteractor interactor,
            Publisher<FeatureCollection> publisher,
            SelectFeatureInteractor selectFeatureInteractor
    ) {
        return new QuakeListPresenter(view, interactor, publisher, selectFeatureInteractor);
    }
}
