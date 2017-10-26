package nz.co.codebros.quakesnz.list;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractorImpl;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository;
import nz.co.codebros.quakesnz.repository.FeatureRepository;
import nz.co.codebros.quakesnz.ui.FeatureAdapter;

/**
 * Created by leandro on 9/07/15.
 */
@Module
public abstract class QuakeListModule {

    @Binds
    abstract QuakeListView quakeListView(QuakeListFragment fragment);

    @Binds
    abstract FeatureAdapter.Listener featureAdapterListener(QuakeListFragment fragment);

    @Provides
    static SelectFeatureInteractor selectFeatureInteractor(Subject<Feature> subject) {
        return new SelectFeatureInteractorImpl(subject);
    }
}
