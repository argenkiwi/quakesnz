package nz.co.codebros.quakesnz.list;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractorImpl;
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository;
import nz.co.codebros.quakesnz.repository.FeatureRepository;
import nz.co.codebros.quakesnz.ui.FeatureAdapter;

/**
 * Created by Leandro on 2/11/2017.
 */
@Module
public abstract class QuakeListModule {

    @Binds
    abstract QuakeListView quakeListView(QuakeListFragment fragment);

    @Binds
    abstract FeatureAdapter.Listener listener(QuakeListFragment fragment);

    @Provides
    static SelectFeatureInteractor selectFeatureInteractor(Subject<Feature> featureSubject) {
        return new SelectFeatureInteractorImpl(featureSubject);
    }

    @Provides
    static QuakeListViewModel.Factory viewModelFactory(
            FeatureCollectionRepository repository,
            LoadFeaturesInteractor interactor
    ) {
        return new QuakeListViewModel.Factory(repository, interactor);
    }

    @Provides
    static QuakeListViewModel viewModel(
            QuakeListFragment fragment,
            QuakeListViewModel.Factory factory
    ) {
        return ViewModelProviders.of(fragment, factory).get(QuakeListViewModel.class);
    }

    @Provides
    static QuakeListPresenter quakeListPresenter(
            QuakeListView view,
            FeatureRepository featureRepository,
            SelectFeatureInteractor selectFeatureInteractor
    ) {
        return new QuakeListPresenter(view, featureRepository, selectFeatureInteractor);
    }
}
