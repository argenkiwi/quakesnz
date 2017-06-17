package nz.co.codebros.quakesnz.list;

import java.util.ArrayList;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.repository.Repository;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenter {

    private final QuakeListView view;
    private final GetFeaturesInteractor interactor;
    private final Repository<FeatureCollection> featureCollectionRepository;
    private final ArrayList<Disposable> disposables = new ArrayList<>();

    QuakeListPresenter(QuakeListView view, GetFeaturesInteractor interactor,
                       Repository<FeatureCollection> featureCollectionRepository) {
        this.view = view;
        this.interactor = interactor;
        this.featureCollectionRepository = featureCollectionRepository;
    }

    void onCreateView() {
        disposables.add(featureCollectionRepository.subscribe(new Consumer<FeatureCollection>() {
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
        interactor.execute(new CompletableObserver() {
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