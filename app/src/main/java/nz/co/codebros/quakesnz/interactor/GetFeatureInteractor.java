package nz.co.codebros.quakesnz.interactor;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by leandro on 7/07/16.
 */
public class GetFeatureInteractor {
    private final GeonetService service;

    private Subscription subscription = Subscriptions.empty();

    public GetFeatureInteractor(GeonetService service) {
        this.service = service;
    }

    public void cancel() {
        if (!subscription.isUnsubscribed()) subscription.unsubscribe();
    }

    public void execute(Observer<Feature> subscriber, String publicID) {
        subscription = service.getQuake(publicID)
                .map(new Func1<FeatureCollection, Feature>() {
                    @Override
                    public Feature call(FeatureCollection featureCollection) {
                        return featureCollection.getFeatures().length > 0
                                ? featureCollection.getFeatures()[0] : null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
