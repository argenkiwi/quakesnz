package nz.co.codebros.quakesnz.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.core.model.FeatureCollection;
import nz.co.codebros.quakesnz.repository.FeatureCollectionRepository;

/**
 * Created by Leandro on 28/10/2017.
 */

class QuakeListViewModel extends ViewModel {

    private final FeatureCollectionRepository repository;
    private MutableLiveData<List<Feature>> features;

    QuakeListViewModel(FeatureCollectionRepository repository) {
        this.repository = repository;
    }

    LiveData<List<Feature>> getFeatures() {
        if (features == null) {
            features = new MutableLiveData<>();
            repository.subscribe(new Consumer<FeatureCollection>() {
                @Override
                public void accept(FeatureCollection featureCollection) throws Exception {
                    features.setValue(featureCollection.getFeatures());
                }
            });
        }
        return features;
    }

    static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final FeatureCollectionRepository repository;

        Factory(FeatureCollectionRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new QuakeListViewModel(repository);
        }
    }
}
