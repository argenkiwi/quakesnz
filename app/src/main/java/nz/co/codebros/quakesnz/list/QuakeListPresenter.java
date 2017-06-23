package nz.co.codebros.quakesnz.list;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.presenter.BasePresenter;
import nz.co.codebros.quakesnz.publisher.Publisher;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenter extends BasePresenter {

    private final QuakeListView view;
    private final LoadFeaturesInteractor loadFeaturesInteractor;
    private final Publisher<FeatureCollection> featureCollectionPublisher;
    private final SelectFeatureInteractor selectFeatureInteractor;

    QuakeListPresenter(QuakeListView view, LoadFeaturesInteractor loadFeaturesInteractor,
                       Publisher<FeatureCollection> featureCollectionPublisher,
                       SelectFeatureInteractor selectFeatureInteractor) {
        this.view = view;
        this.loadFeaturesInteractor = loadFeaturesInteractor;
        this.featureCollectionPublisher = featureCollectionPublisher;
        this.selectFeatureInteractor = selectFeatureInteractor;
    }

    void onViewCreated() {
        addDisposable(featureCollectionPublisher.subscribe(new Consumer<FeatureCollection>() {
            @Override
            public void accept(@NonNull FeatureCollection featureCollection) throws Exception {
                view.listQuakes(featureCollection.getFeatures());
            }
        }));
    }

    void onDestroyView() {
        disposeAll();
    }

    void onRefresh() {
        loadFeaturesInteractor.execute(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                view.showProgress();
                addDisposable(d);
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

    void onFeatureSelected(Feature feature) {
        selectFeatureInteractor.execute(feature);
    }
}