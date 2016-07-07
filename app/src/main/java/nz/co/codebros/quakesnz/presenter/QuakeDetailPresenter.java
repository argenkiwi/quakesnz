package nz.co.codebros.quakesnz.presenter;

import nz.co.codebros.quakesnz.interactor.GetFeatureInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.view.QuakeDetailView;
import rx.Observer;

/**
 * Created by leandro on 7/07/16.
 */
public class QuakeDetailPresenter implements Observer<Feature> {
    private final QuakeDetailView view;
    private final GetFeatureInteractor interactor;
    private Feature feature;

    public QuakeDetailPresenter(QuakeDetailView view, GetFeatureInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        view.showLoadingError();
    }

    public void onInit(Feature feature) {
        this.feature = feature;
        view.showDetails(feature);
    }

    public void onInit(String publicID) {
        interactor.execute(this, publicID);
    }

    @Override
    public void onNext(Feature feature) {
        this.feature = feature;
        view.showDetails(feature);
    }

    public void onShare() {
        if (feature != null) view.share(feature);
    }

    public void onStop() {
        interactor.cancel();
    }
}
