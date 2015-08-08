package nz.co.codebros.quakesnz.interactor;

import nz.co.codebros.quakesnz.model.Feature;
import retrofit.client.Response;

/**
 * Created by leandro on 9/07/15.
 */
public interface LoadQuakesInteractor {
    void loadQuakes(OnQuakesLoadedListener listener);

    void saveQuakes(Response response, OnQuakesSavedListener listener);

    interface OnQuakesLoadedListener{
        void onLoadQuakesFailure();
        void onLoadQuakesSuccess(Feature[] features);
    }

    interface OnQuakesSavedListener{
        void onSaveQuakesFailure();
        void onSaveQuakesSuccess();
    }

}
