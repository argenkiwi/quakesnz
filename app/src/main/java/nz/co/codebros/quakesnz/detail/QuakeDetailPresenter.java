package nz.co.codebros.quakesnz.detail;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import nz.co.codebros.quakesnz.interactor.GetFeatureInteractor;
import nz.co.codebros.quakesnz.model.Feature;

/**
 * Created by leandro on 7/07/16.
 */
public class QuakeDetailPresenter implements SingleObserver<Feature> {
    private final QuakeDetailView view;
    private final GetFeatureInteractor interactor;
    private Disposable subscription;

    QuakeDetailPresenter(QuakeDetailView view, GetFeatureInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.subscription = d;
    }

    @Override
    public void onSuccess(@NonNull Feature feature) {
        view.showDetails(feature);
    }

    @Override
    public void onError(Throwable e) {
        view.showLoadingError();
    }

    void onInit(Feature feature) {
        view.showDetails(feature);
    }

    void onInit(String publicID) {
        interactor.execute(this, publicID);
    }

    void onShare(Feature feature) {
        view.share(feature);
    }

    void onDestroyView() {
        if (subscription != null) subscription.dispose();
    }
}
