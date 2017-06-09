package nz.co.codebros.quakesnz.list;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractorImpl;

/**
 * Created by leandro on 9/07/15.
 */
@Module
public class QuakeListModule {
    private QuakeListView view;

    public QuakeListModule(QuakeListView view) {
        this.view = view;
    }

    @Provides
    public GetFeaturesInteractor provideInteractor(GeonetService service,
                                                   SharedPreferences preferences) {
        return new GetFeaturesInteractorImpl(service, preferences);
    }

    @Provides
    public QuakeListPresenter providePresenter(GetFeaturesInteractor interactor) {
        return new QuakeListPresenter(view, interactor);
    }
}
