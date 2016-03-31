package nz.co.codebros.quakesnz;

import android.content.SharedPreferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import nz.co.codebros.quakesnz.dispatcher.GetQuakesDispatcher;
import nz.co.codebros.quakesnz.event.GetQuakesRequestEvent;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit2.Call;

/**
 * Created by leandro on 8/08/15.
 */
public class RequestHandler {
    private EventBus mBus;
    private SharedPreferences mSharedPreferences;
    private GeonetService mService;

    public RequestHandler(EventBus bus, SharedPreferences sharedPreferences, GeonetService service) {
        mBus = bus;
        mSharedPreferences = sharedPreferences;
        mService = service;
    }

    @Subscribe
    public void onEvent(GetQuakesRequestEvent event) {
        String filter = mSharedPreferences.getString("pref_filter", "felt");
        Call<FeatureCollection> call = mService.listAllQuakes(filter);
        call.enqueue(new GetQuakesDispatcher(mBus));
    }
}
