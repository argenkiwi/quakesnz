package nz.co.codebros.quakesnz.interactor;

import android.content.SharedPreferences;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import rx.Observable;
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
    private final SharedPreferences preferences;

    private Subscription subscription = Subscriptions.empty();

    public GetFeaturesInteractor(GeonetService service, SharedPreferences preferences) {
        this.service = service;
        this.preferences = preferences;
    }

    public void cancel() {
        if (!subscription.isUnsubscribed()) subscription.unsubscribe();
    }

    public void execute(Observer<FeatureCollection> subscriber) {
        final int mmi = Integer.parseInt(preferences.getString("pref_intensity", "3"));

        final Observable<FeatureCollection> networkStream = service.getQuakes(mmi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        final Observable<FeatureCollection> cacheStream = service.getQuakesCached(mmi)
                .onErrorReturn(new Func1<Throwable, FeatureCollection>() {
                    @Override
                    public FeatureCollection call(Throwable throwable) {
                        return new FeatureCollection();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        subscription = Observable.mergeDelayError(cacheStream, networkStream).subscribe(subscriber);
    }
}
