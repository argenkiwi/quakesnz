package nz.co.codebros.quakesnz.interactor;

import java.io.IOException;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.utils.LoadCitiesHelper;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by leandro on 2/04/16.
 */
public class GetFeaturesInteractor {
    private final GeonetService service;
    private final LoadCitiesHelper helper;
    private Subscription subscription = Subscriptions.empty();

    public GetFeaturesInteractor(GeonetService service, LoadCitiesHelper helper) {
        this.service = service;
        this.helper = helper;
    }

    public void execute(String filter, Observer<? super Feature[]> subscriber) {
        subscription = service.listAllQuakes(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<FeatureCollection, Feature[]>() {
                    @Override
                    public Feature[] call(FeatureCollection featureCollection) {
                        try {
                            return helper.execute(featureCollection.getFeatures());
                        } catch (IOException e) {
                            return new Feature[0];
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void cancel() {
        if (!subscription.isUnsubscribed()) subscription.unsubscribe();
    }
}
