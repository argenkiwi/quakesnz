package nz.co.codebros.quakesnz.list;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractorImpl;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository;

/**
 * Created by leandro on 9/07/15.
 */
@Module
public abstract class QuakeListModule {

    @Binds
    abstract QuakeListView quakeListView(QuakeListFragment fragment);

    @Provides
    static SelectFeatureInteractor selectFeatureInteractor(Subject<Feature> subject) {
        return new SelectFeatureInteractorImpl(subject);
    }

    @Provides
    static QuakeListPresenter presenter(
            QuakeListView view,
            FeatureCollectionRepository featureCollectionRepository,
            LoadFeaturesInteractor loadFeaturesInteractor,
            SelectFeatureInteractor selectFeatureInteractor
    ) {
        return new QuakeListPresenter(view, featureCollectionRepository, loadFeaturesInteractor,
                selectFeatureInteractor);
    }
}
