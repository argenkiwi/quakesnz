package nz.co.codebros.quakesnz.presenter;

import android.util.Log;

import nz.co.codebros.quakesnz.interactor.LoadQuakesInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.ui.QuakeListView;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenterImpl implements QuakeListPresenter, LoadQuakesInteractor.Listener {

    private static final String TAG = QuakeListPresenterImpl.class.getSimpleName();
    private final LoadQuakesInteractor mInteractor;
    private QuakeListView mView;

    public QuakeListPresenterImpl(LoadQuakesInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onLoadQuakes() {
        Log.d(TAG, "Load quakes.");
        mView.showProgress();
        mInteractor.loadQuakes(this);
    }

    @Override
    public void onQuakesDownloaded() {
        Log.d(TAG, "Quakes downloaded.");
        mInteractor.loadQuakes(this);
    }

    @Override
    public void onQuakesLoaded(Feature[] features) {
        Log.d(TAG, "Quakes loaded.");
        if (mView != null) {
            mView.hideProgress();
            mView.listQuakes(features);
        }
    }

    @Override
    public void onQuakesLoadFailed() {
        Log.d(TAG, "Load failed.");
        if (mView != null) {
            mView.hideProgress();
        }
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "Refresh.");
        mView.showProgress();
        mInteractor.downloadQuakes(this);
    }

    @Override
    public void bindView(QuakeListView view) {
        mView = view;
    }

    @Override
    public void unbindView() {
        mView = null;
    }
}
