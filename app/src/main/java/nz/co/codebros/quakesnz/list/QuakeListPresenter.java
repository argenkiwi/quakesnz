package nz.co.codebros.quakesnz.list;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.model.FeatureCollection;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenter implements SingleObserver<FeatureCollection> {

    private final QuakeListView view;
    private final GetFeaturesInteractor interactor;

    private Disposable subscription;

    QuakeListPresenter(QuakeListView view, GetFeaturesInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onError(Throwable e) {
        view.hideProgress();
        view.showError();
    }

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        this.subscription = disposable;
    }

    @Override
    public void onSuccess(@NonNull FeatureCollection featureCollection) {
        view.listQuakes(featureCollection.getFeatures());
        view.hideProgress();
    }

    void onDestroyView() {
        if (subscription != null) subscription.dispose();
    }

    void onRefresh() {
        view.showProgress();
        interactor.execute(this);
    }
}