package nz.co.codebros.quakesnz.module;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;
import nz.co.codebros.quakesnz.interactor.LoadQuakesInteractor;
import nz.co.codebros.quakesnz.presenter.QuakeListPresenter;

/**
 * Created by leandro on 9/07/15.
 */
@Module
public class QuakeListModule {
    @Provides
    public LoadQuakesInteractor provideInteractor(Context context, Gson gson) {
        return new LoadQuakesInteractor(context, gson);
    }

    @Provides
    public QuakeListPresenter providePresenter(EventBus bus, LoadQuakesInteractor interactor) {
        return new QuakeListPresenter(bus, interactor);
    }
}
