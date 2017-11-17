package nz.co.codebros.quakesnz.list;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.Subject;
import nz.co.codebros.quakesnz.core.data.Feature;
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
    abstract FeatureAdapter.Listener listener(QuakeListFragment fragment);

    @Provides
    static SelectFeatureInteractor selectFeatureInteractor(Subject<Feature> featureSubject) {
        return new SelectFeatureInteractorImpl(featureSubject);
    }

    @Provides
    static QuakeListViewModel.Factory viewModelFactory(
            FeatureCollectionRepository featureCollectionRepository,
            FeatureRepository featureRepository,
            LoadFeaturesInteractor loadFeaturesInteractor,
            SelectFeatureInteractor selectFeatureInteractor
    ) {
        return new QuakeListViewModel.Factory(
                featureCollectionRepository,
                featureRepository,
                loadFeaturesInteractor,
                selectFeatureInteractor
        );
    }

    @Provides
    static QuakeListViewModel viewModel(
            QuakeListFragment fragment,
            QuakeListViewModel.Factory factory
    ) {
        return ViewModelProviders.of(fragment, factory).get(QuakeListViewModel.class);
    }
}
