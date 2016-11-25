package nz.co.codebros.quakesnz.presenter;

import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.view.QuakeListView;
import rx.Observer;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenter implements Observer<FeatureCollection> {

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
        view.hideProgress();
        view.showDownloadFailedMessage();
    }

    @Override
    public void onNext(FeatureCollection featureCollection) {
        view.listQuakes(featureCollection.getFeatures());
    }

    public void onRefresh() {
        view.showProgress();
        interactor.execute(this);
    }
}