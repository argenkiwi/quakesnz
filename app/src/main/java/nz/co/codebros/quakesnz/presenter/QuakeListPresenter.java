package nz.co.codebros.quakesnz.presenter;

import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import nz.co.codebros.quakesnz.event.GetQuakesFailureEvent;
import nz.co.codebros.quakesnz.event.GetQuakesRequestEvent;
import nz.co.codebros.quakesnz.event.GetQuakesSuccessEvent;
import nz.co.codebros.quakesnz.interactor.LoadQuakesInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.ui.QuakeListView;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenter extends BasePresenter<QuakeListView>
        implements LoadQuakesInteractor.OnQuakesSavedListener,
        LoadQuakesInteractor.OnQuakesLoadedListener {
    private static final String TAG = QuakeListPresenter.class.getSimpleName();
    private EventBus mBus;
    private LoadQuakesInteractor mInteractor;

    public QuakeListPresenter(EventBus bus, LoadQuakesInteractor interactor) {
        mBus = bus;
        mInteractor = interactor;
    }

    @Subscribe
    public void onEvent(GetQuakesFailureEvent event) {
        Log.d(TAG, "Get quakes failure.");
        if (getView() != null) {
            getView().hideProgress();
            getView().showDownloadFailedMessage();
        }
        mInteractor.loadQuakes(this);
    }

    @Subscribe
    public void onEvent(GetQuakesSuccessEvent event) {
        Log.d(TAG, "Get quakes success.");
//        mInteractor.saveQuakes(event.getResponse(), this);
        mInteractor.loadQuakes(event.getData().getFeatures(), this);
    }

    public void onLoad() {
        mInteractor.loadQuakes(this);
    }

    public void onFeatureClicked(View view, Feature feature) {
        Log.d(TAG, "Feature clicked.");
        getView().showQuakeDetail(view, feature);
    }

    public void onRefresh() {
        Log.d(TAG, "Refresh.");
        getView().showProgress();
        mBus.post(new GetQuakesRequestEvent());
    }

    @Override
    public void onSaveQuakesFailure() {
        Log.w(TAG, "Save quakes failure.");
    }

    @Override
    public void onSaveQuakesSuccess() {
        Log.d(TAG, "Save quakes success.");
    }

    @Override
    public void onLoadQuakesFailure() {
        if (getView() != null) {
            getView().hideProgress();
            getView().showLoadFailedMessage();
        }
    }

    @Override
    public void onLoadQuakesSuccess(Feature[] features) {
        if (getView() != null) {
            getView().hideProgress();
            getView().listQuakes(features);
        }
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        mBus.register(this);
    }

    @Override
    protected void onUnbindView() {
        super.onUnbindView();
        mBus.unregister(this);
    }
}