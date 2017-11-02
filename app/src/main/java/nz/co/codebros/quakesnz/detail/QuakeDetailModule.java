package nz.co.codebros.quakesnz.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.repository.FeatureRepository;

/**
 * Created by Leandro on 2/11/2017.
 */
@Module
public abstract class QuakeDetailModule {

    @Binds
    abstract QuakeDetailView quakeDetailView(QuakeDetailFragment fragment);

    @Provides
    static QuakeDetailViewModel.Factory factory(FeatureRepository repository) {
        return new QuakeDetailViewModel.Factory(repository);
    }

    @Provides
    static QuakeDetailViewModel quakeDetailViewModel(
            QuakeDetailFragment fragment,
            QuakeDetailViewModel.Factory factory
    ) {
        return ViewModelProviders.of(fragment, factory).get(QuakeDetailViewModel.class);
    }
}
