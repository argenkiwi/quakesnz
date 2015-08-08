package nz.co.codebros.quakesnz;

import de.greenrobot.event.EventBus;
import nz.co.codebros.quakesnz.dispatcher.GetQuakesDispatcher;
import nz.co.codebros.quakesnz.event.GetQuakesRequestEvent;

/**
 * Created by leandro on 8/08/15.
 */
public class RequestHandler {
    private EventBus mBus;
    private GeonetService mService;

    public RequestHandler(EventBus bus, GeonetService service) {
        mBus = bus;
        mService = service;
    }

    public void onEvent(GetQuakesRequestEvent event){
        mService.listAllQuakes("felt", new GetQuakesDispatcher(mBus));
    }
}
