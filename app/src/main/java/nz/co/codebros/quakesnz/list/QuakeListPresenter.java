package nz.co.codebros.quakesnz.list;

import java.util.ArrayList;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.repository.Publisher;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenter {

    private final QuakeListView view;
    private final LoadFeaturesInteractor loadFeaturesInteractor;
    private final Publisher<FeatureCollection> featureCollectionPublisher;
    private final ArrayList<Disposable> disposables = new ArrayList<>();

    QuakeListPresenter(QuakeListView view, LoadFeaturesInteractor loadFeaturesInteractor,
                       Publisher<FeatureCollection> featureCollectionPublisher) {
        this.view = view;
        this.loadFeaturesInteractor = loadFeaturesInteractor;
        this.featureCollectionPublisher = featureCollectionPublisher;
    }

    void onCreateView() {
        disposables.add(featureCollectionPublisher.subscribe(new Consumer<FeatureCollection>() {
            @Override
            public void accept(@NonNull FeatureCollection featureCollection) throws Exception {
                view.listQuakes(featureCollection.getFeatures());
            }
        }));
    }

    void onDestroyView() {
        while (!disposables.isEmpty()) {
            disposables.remove(0).dispose();
        }
    }

    void onRefresh() {
        loadFeaturesInteractor.execute(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                view.showProgress();
                disposables.add(d);
            }

            @Override
            public void onComplete() {
                view.hideProgress();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                view.hideProgress();
                view.showError();
            }
        });
    }
}