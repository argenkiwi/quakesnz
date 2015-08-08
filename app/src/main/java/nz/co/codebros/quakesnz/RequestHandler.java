package nz.co.codebros.quakesnz;

import android.content.SharedPreferences;

import de.greenrobot.event.EventBus;
import nz.co.codebros.quakesnz.dispatcher.GetQuakesDispatcher;
import nz.co.codebros.quakesnz.event.GetQuakesRequestEvent;

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

    public void onEvent(GetQuakesRequestEvent event){
        String filter = mSharedPreferences.getString("pref_filter", "felt");
        mService.listAllQuakes(filter, new GetQuakesDispatcher(mBus));
    }
}
