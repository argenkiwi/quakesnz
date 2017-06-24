package nz.co.codebros.quakesnz.detail;

import android.os.Bundle;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.presenter.BasePresenter;
import nz.co.codebros.quakesnz.repository.BundleRepository;

/**
 * Created by leandro on 7/07/16.
 */
public class QuakeDetailPresenter extends BasePresenter{
    private final QuakeDetailView view;
    private final BundleRepository<Feature> repository;
    private final LoadFeatureInteractor interactor;

    QuakeDetailPresenter(
            QuakeDetailView view,
            BundleRepository<Feature> repository,
            LoadFeatureInteractor interactor
    ) {
        this.view = view;
        this.repository = repository;
        this.interactor = interactor;
    }

    void onRefresh(String publicID) {
        addDisposable(interactor.execute(publicID, new Action() {
            @Override
            public void run() throws Exception {
                // TODO Do something!
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                view.showLoadingError();
            }
        }));
    }

    @Override
    public void onViewCreated() {
        addDisposable(repository.subscribe(new Consumer<Feature>() {
            @Override
            public void accept(@NonNull Feature feature) throws Exception {
                view.showDetails(feature);
            }
        }));
    }

    @Override
    public void onViewRestored(Bundle bundle) {
        repository.publish(bundle);
        onViewCreated();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        addDisposable(repository.subscribe(bundle));
    }

    @Override
    public void onDestroyView() {
        disposeAll();
    }

    void onShare(Feature feature) {
        view.share(feature);
    }
}
