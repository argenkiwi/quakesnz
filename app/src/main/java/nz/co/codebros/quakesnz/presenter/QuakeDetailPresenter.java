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

    public QuakeDetailPresenter(QuakeDetailView view, GetFeatureInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void onInit(Feature feature) {
        view.showDetails(feature);
    }

    public void onInit(String publicID) {
        interactor.execute(this, publicID);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        // TODO Display loading error.
    }

    @Override
    public void onNext(Feature feature) {
        view.showDetails(feature);
    }

    public void onStop() {
        interactor.cancel();
    }
}
