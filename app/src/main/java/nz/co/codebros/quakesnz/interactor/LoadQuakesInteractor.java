package nz.co.codebros.quakesnz.interactor;

import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.presenter.QuakeListPresenterImpl;

/**
 * Created by leandro on 9/07/15.
 */
public interface LoadQuakesInteractor {
    void loadQuakes(Listener listener);

    void downloadQuakes(Listener listener);

    interface Listener {
        void onQuakesLoaded(Feature[] features);

        void onQuakesDownloaded();

        void onQuakesLoadFailed();
    }
}
