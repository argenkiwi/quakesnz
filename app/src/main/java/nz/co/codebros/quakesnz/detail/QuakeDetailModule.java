package nz.co.codebros.quakesnz.detail;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl;
import nz.co.codebros.quakesnz.repository.FeatureRepository;

/**
 * Created by leandro on 7/07/16.
 */
@Module
public abstract class QuakeDetailModule {

    @Binds
    abstract QuakeDetailView quakeDetailView(QuakeDetailFragment fragment);
}
