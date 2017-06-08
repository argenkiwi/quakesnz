package nz.co.codebros.quakesnz.interactor;

import android.content.SharedPreferences;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.model.FeatureCollection;

/**
 * Created by leandro on 2/04/16.
 */
public class GetFeaturesInteractor {
    private final GeonetService service;
    private final SharedPreferences preferences;

    public GetFeaturesInteractor(GeonetService service, SharedPreferences preferences) {
        this.service = service;
        this.preferences = preferences;
    }

    public void execute(SingleObserver<FeatureCollection> subscriber) {
        final int mmi = Integer.parseInt(preferences.getString("pref_intensity", "3"));
        service.getQuakes(mmi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
