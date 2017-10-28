package nz.co.codebros.quakesnz.list;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractorImpl;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository;
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

    @Provides
    static QuakeListViewModel.Factory viewModelFactory(FeatureCollectionRepository repository) {
        return new QuakeListViewModel.Factory(repository);
    }

    @Provides
    static QuakeListViewModel viewModel(
            QuakeListFragment fragment,
            QuakeListViewModel.Factory factory
    ) {
        return ViewModelProviders.of(fragment, factory).get(QuakeListViewModel.class);
    }
}
