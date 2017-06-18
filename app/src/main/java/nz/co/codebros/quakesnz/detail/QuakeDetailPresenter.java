package nz.co.codebros.quakesnz.detail;

import java.util.ArrayList;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.repository.Publisher;

/**
 * Created by leandro on 7/07/16.
 */
public class QuakeDetailPresenter {
    private final QuakeDetailView view;
    private final Publisher<Feature> publisher;
    private final LoadFeatureInteractorImpl interactor;
    private final ArrayList<Disposable> disposables = new ArrayList<>();

    QuakeDetailPresenter(
            QuakeDetailView view,
            Publisher<Feature> publisher,
            LoadFeatureInteractorImpl interactor
    ) {
        this.view = view;
        this.publisher = publisher;
        this.interactor = interactor;
    }

    void onRefresh(String publicID) {
        interactor.execute(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                view.showLoadingError();
            }
        }, publicID);
    }

    void onCreateView() {
        disposables.add(publisher.subscribe(new Consumer<Feature>() {
            @Override
            public void accept(@NonNull Feature feature) throws Exception {
                view.showDetails(feature);
            }
        }));
    }

    void onDestroyView() {
        while (!disposables.isEmpty()) {
            disposables.remove(0).dispose();
        }
    }

    void onShare(Feature feature) {
        view.share(feature);
    }
}
