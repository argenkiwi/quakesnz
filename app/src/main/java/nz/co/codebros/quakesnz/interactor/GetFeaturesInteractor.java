package nz.co.codebros.quakesnz.interactor;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by leandro on 2/04/16.
 */
public class GetFeaturesInteractor {

    private final GeonetService service;
    private Subscription subscription = Subscriptions.empty();

    public GetFeaturesInteractor(GeonetService service) {
        this.service = service;
    }

    public void cancel() {
        if (!subscription.isUnsubscribed()) subscription.unsubscribe();
    }

    public void execute(Observer<FeatureCollection> subscriber) {
        subscription = service.listAllQuakes(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
