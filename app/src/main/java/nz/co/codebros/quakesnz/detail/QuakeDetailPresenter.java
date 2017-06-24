package nz.co.codebros.quakesnz.detail;

import android.os.Bundle;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.presenter.BasePresenter;
import nz.co.codebros.quakesnz.publisher.Publisher;

/**
 * Created by leandro on 7/07/16.
 */
public class QuakeDetailPresenter extends BasePresenter{
    private final QuakeDetailView view;
    private final Publisher<Feature> publisher;
    private final LoadFeatureInteractor interactor;

    QuakeDetailPresenter(
            QuakeDetailView view,
            Publisher<Feature> publisher,
            LoadFeatureInteractor interactor
    ) {
        this.view = view;
        this.publisher = publisher;
        this.interactor = interactor;
    }

    void onRefresh(String publicID) {
        interactor.execute(publicID, new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                view.showLoadingError();
            }
        });
    }

    @Override
    public void onViewCreated() {
        addDisposable(publisher.subscribe(new Consumer<Feature>() {
            @Override
            public void accept(@NonNull Feature feature) throws Exception {
                view.showDetails(feature);
            }
        }));
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void onViewRestored(Bundle bundle) {

    }

    void onDestroyView() {
        disposeAll();
    }

    void onShare(Feature feature) {
        view.share(feature);
    }
}
