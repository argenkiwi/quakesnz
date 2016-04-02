package nz.co.codebros.quakesnz.presenter;

import android.util.Log;
import android.view.View;

import java.io.IOException;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.utils.LoadCitiesHelper;
import nz.co.codebros.quakesnz.view.QuakeListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenter {

    private static final String TAG = QuakeListPresenter.class.getSimpleName();

    private final QuakeListView view;
    private final GeonetService service;
    private final LoadCitiesHelper helper;

    private Call<FeatureCollection> call;

    public QuakeListPresenter(QuakeListView view, GeonetService service, LoadCitiesHelper helper) {
        this.view = view;
        this.service = service;
        this.helper = helper;
    }

    public void onDestroyView() {
        call.cancel();
    }

    public void onFeaturesFailedToLoad() {
        view.hideProgress();
        view.showDownloadFailedMessage();
    }

    public void onFeaturesLoaded(Feature[] features) {
        try {
            helper.execute(features);
            view.listQuakes(features);
        } catch (IOException e) {
            Log.e(TAG, "Failed to load cities", e);
            view.showLoadFailedMessage();
        }
        view.hideProgress();
    }

    public void onRefresh(String filter) {
        view.showProgress();
        this.call = service.listAllQuakes(filter);
        call.enqueue(new Callback());
    }

    public void onViewCreated(String filter) {
        view.showProgress();
        this.call = service.listAllQuakes(filter);
        call.enqueue(new Callback());
    }

    private class Callback implements retrofit2.Callback<FeatureCollection> {

        @Override
        public void onFailure(Call<FeatureCollection> call, Throwable t) {
            onFeaturesFailedToLoad();
        }

        @Override
        public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
            onFeaturesLoaded(response.body().getFeatures());
        }
    }
}