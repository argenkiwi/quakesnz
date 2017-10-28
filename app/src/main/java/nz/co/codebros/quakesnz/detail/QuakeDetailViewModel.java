package nz.co.codebros.quakesnz.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.repository.FeatureRepository;

/**
 * Created by Leandro on 27/10/2017.
 */

class QuakeDetailViewModel extends ViewModel {
    private final FeatureRepository repository;
    private MutableLiveData<Feature> feature;
    private Disposable disposable;

    QuakeDetailViewModel(FeatureRepository repository) {
        this.repository = repository;
    }

    public LiveData<Feature> getFeature() {
        if (feature == null) {
            feature = new MutableLiveData<>();
            disposable = repository.subscribe(new Consumer<Feature>() {
                @Override
                public void accept(Feature feature) throws Exception {
                    QuakeDetailViewModel.this.feature.setValue(feature);
                }
            });
        }
        return feature;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) disposable.dispose();
    }

    static class Factory implements ViewModelProvider.Factory {

        private final FeatureRepository repository;

        Factory(FeatureRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new QuakeDetailViewModel(repository);
        }
    }
}
