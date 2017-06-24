package nz.co.codebros.quakesnz.list;

import android.os.Bundle;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.presenter.BasePresenter;
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenter extends BasePresenter {

    private final QuakeListView view;
    private final FeatureCollectionRepository featureCollectionRepository;
    private final LoadFeaturesInteractor loadFeaturesInteractor;
    private final SelectFeatureInteractor selectFeatureInteractor;
    private Consumer<FeatureCollection> featureCollectionConsumer = new Consumer<FeatureCollection>() {
        @Override
        public void accept(@NonNull FeatureCollection featureCollection) throws Exception {
            view.listQuakes(featureCollection.getFeatures());
        }
    };

    QuakeListPresenter(
            QuakeListView view,
            FeatureCollectionRepository featureCollectionRepository,
            LoadFeaturesInteractor loadFeaturesInteractor,
            SelectFeatureInteractor selectFeatureInteractor
    ) {
        this.view = view;
        this.featureCollectionRepository = featureCollectionRepository;
        this.loadFeaturesInteractor = loadFeaturesInteractor;
        this.selectFeatureInteractor = selectFeatureInteractor;
    }

    void onRefresh() {
        view.showProgress();
        addDisposable(loadFeaturesInteractor.execute(new Action() {
            @Override
            public void run() throws Exception {
                view.hideProgress();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                view.hideProgress();
                view.showError();
            }
        }));
    }

    void onFeatureSelected(Feature feature) {
        selectFeatureInteractor.execute(feature);
    }

    @Override
    public void onViewCreated() {
        addDisposable(featureCollectionRepository.subscribe(featureCollectionConsumer));
    }

    @Override
    public void onViewRestored(Bundle bundle) {
        featureCollectionRepository.publish(bundle);
        onViewCreated();
    }

    @Override
    public void onSaveInstanceState(final Bundle bundle) {
        addDisposable(featureCollectionRepository.subscribe(bundle));
    }

    @Override
    public void onDestroyView() {
        disposeAll();
    }
}