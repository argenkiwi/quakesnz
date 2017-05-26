package nz.co.codebros.quakesnz.interactor;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;

/**
 * Created by leandro on 7/07/16.
 */
public class GetFeatureInteractor {
    private final GeonetService service;

    public GetFeatureInteractor(GeonetService service) {
        this.service = service;
    }

    public void execute(Observer<Feature> subscriber, String publicID) {
       service.getQuake(publicID)
                .map(new Function<FeatureCollection, Feature>() {
                    @Override
                    public Feature apply(@NonNull FeatureCollection featureCollection) throws Exception {
                        return featureCollection.getFeatures().length > 0
                                ? featureCollection.getFeatures()[0] : null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
