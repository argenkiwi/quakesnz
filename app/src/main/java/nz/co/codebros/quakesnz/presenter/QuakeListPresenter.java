package nz.co.codebros.quakesnz.presenter;

import android.util.Log;
import android.view.View;

import java.io.IOException;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.utils.LoadCitiesHelper;
import nz.co.codebros.quakesnz.view.QuakeListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenter implements Observer<Feature[]> {

    private static final String TAG = QuakeListPresenter.class.getSimpleName();

    private final QuakeListView view;
    private final GetFeaturesInteractor interactor;

    public QuakeListPresenter(QuakeListView view, GetFeaturesInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCompleted() {
        view.hideProgress();
    }

    public void onDestroyView() {
        interactor.cancel();
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Failed to load cities", e);
        view.hideProgress();
        view.showLoadFailedMessage();
    }

    public void onFeaturesFailedToLoad() {
        view.showDownloadFailedMessage();
    }

    @Override
    public void onNext(Feature[] features) {
        view.listQuakes(features);
    }

    public void onRefresh(String filter) {
        view.showProgress();
        interactor.execute(filter, this);
    }

    public void onViewCreated(String filter) {
        view.showProgress();
        interactor.execute(filter, this);
    }
}