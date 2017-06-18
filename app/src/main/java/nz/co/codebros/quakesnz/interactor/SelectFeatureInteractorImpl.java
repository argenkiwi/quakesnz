package nz.co.codebros.quakesnz.interactor;

import io.reactivex.Observer;
import nz.co.codebros.quakesnz.model.Feature;

/**
 * Created by leandro on 19/06/17.
 */

public class SelectFeatureInteractorImpl implements SelectFeatureInteractor {

    private final Observer<Feature> featureObserver;

    public SelectFeatureInteractorImpl(Observer<Feature> featureObserver) {
        this.featureObserver = featureObserver;
    }

    @Override
    public void execute(Feature feature) {
        featureObserver.onNext(feature);
    }
}
