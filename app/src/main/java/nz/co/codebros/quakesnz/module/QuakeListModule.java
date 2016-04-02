package nz.co.codebros.quakesnz.module;

import android.content.Context;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.presenter.QuakeListPresenter;
import nz.co.codebros.quakesnz.utils.LoadCitiesHelper;
import nz.co.codebros.quakesnz.view.QuakeListView;

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
    public LoadCitiesHelper provideHelper(Context context, Gson gson){
        return new LoadCitiesHelper(context, gson);
    }

    @Provides
    public GetFeaturesInteractor provideInteractor(GeonetService service, LoadCitiesHelper helper){
        return new GetFeaturesInteractor(service, helper);
    }

    @Provides
    public QuakeListPresenter providePresenter(GetFeaturesInteractor interactor) {
        return new QuakeListPresenter(view, interactor);
    }
}
