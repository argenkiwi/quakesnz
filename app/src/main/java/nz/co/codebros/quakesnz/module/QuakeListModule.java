package nz.co.codebros.quakesnz.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.LoadQuakesInteractor;
import nz.co.codebros.quakesnz.interactor.LoadQuakesInteractorImpl;
import nz.co.codebros.quakesnz.presenter.QuakeListPresenter;
import nz.co.codebros.quakesnz.presenter.QuakeListPresenterImpl;
import nz.co.codebros.quakesnz.ui.FeatureAdapter;

/**
 * Created by leandro on 9/07/15.
 */
@Module
public class QuakeListModule {

    @Provides
    public LoadQuakesInteractor provideInteractor(Context context, GeonetService service) {
        return new LoadQuakesInteractorImpl(context, service);
    }

    @Provides
    public QuakeListPresenter providePresenter(LoadQuakesInteractor interactor) {
        return new QuakeListPresenterImpl(interactor);
    }
}
